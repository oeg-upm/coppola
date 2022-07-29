package coppola.controller;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Optional;

import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shacl.ShaclValidator;
import org.apache.jena.shacl.Shapes;
import org.apache.jena.shacl.ValidationReport;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import coppola.exceptions.InvalidRequestException;
import coppola.model.ValidationDocument;
import coppola.persistence.Repository;
import spark.Request;
import spark.Response;
import spark.Route;

public class Controller {

	private static Repository<ValidationDocument> repository = new Repository<>(ValidationDocument.class);

	public static final Route list = (Request request, Response response) -> {
		response.status(200);
		response.type("application/json");
		JsonArray array = new JsonArray();
		repository.retrieve().parallelStream().map(doc -> toIdJson(doc.getId())).forEach(elem -> array.add(elem));
		return array ;
	};

	private static JsonObject toIdJson(String id) {
		JsonObject object = new JsonObject();
		object.addProperty("id", id);
		object.addProperty("url", "/api/"+id);
		return object;
	}

	public static final Route get = (Request request, Response response) -> {
		String id = fetchId(request);
		Optional<ValidationDocument> documentOpt = repository.retrieve(id);
		if (documentOpt.isPresent()) {
			response.status(200);
			response.type("text/turtle");
			return documentOpt.get().validationDocument;
		} else {
			response.status(404);
			return "";
		}
	};

	public static final Route create = (Request request, Response response) -> {
		String id = fetchId(request);
		String body = request.body();
		if (body.isBlank())
			throw new InvalidRequestException("A SHACL shape must be provided in the body");
		ValidationDocument doc = new ValidationDocument(id, body);
		response.status(201);
		if (repository.exists(id)) {
			response.status(204);
			repository.delete(id);
		}
		try {
			toGraph(doc.getValidationDocument());
			repository.persist(doc);
			return doc;
		}catch(Exception e) {
			throw new InvalidRequestException(e.toString());
		}
	};

	private static Graph toGraph(String rdf) {
		Model model  = ModelFactory.createDefaultModel();
		model.read(new ByteArrayInputStream(rdf.getBytes()), null, "TURTLE");
		return model.getGraph();
	}

	public static final Route apply = (Request request, Response response) -> {
		String id = fetchId(request);
		String body = request.body();
		// Retrieve
		Optional<ValidationDocument> documentOpt = repository.retrieve(id);
		if (documentOpt.isPresent()) {
			String shacl = documentOpt.get().getValidationDocument();
			Graph shapesGraph = toGraph(shacl);
			Graph dataGraph = toGraph(body);

			Shapes shapes = Shapes.parse(shapesGraph);
			ValidationReport report = ShaclValidator.get().validate(shapes, dataGraph);
			Writer writer = new StringWriter();
			report.getModel().write(writer, "TTL", null);
			return writer.toString();
		} else {
			throw new InvalidRequestException("The id belongs to no validation document");
		}

	};

	public static final Route remove = (Request request, Response response) -> {
		String id = fetchId(request);
		if (repository.exists(id)) {
			repository.delete(id);
			response.status(200);
		} else {
			response.status(404);
		}
		return "";
	};

	protected static final String fetchId(Request request) {
		String id = request.params("id");
		if (id == null || id.isEmpty())
			throw new InvalidRequestException("Missing valid Component id");
		return id;
	}
}
