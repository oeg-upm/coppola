package coppola.model;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.google.gson.JsonObject;

@Entity
public class ValidationDocument {

	@Id
	public String id;
	@Column(name = "validation_doc", columnDefinition = "BLOB")
	@Lob
	public String validationDocument;

	public ValidationDocument(String id, String validationDocument) {
		super();
		this.validationDocument = validationDocument;
		this.id = id;
	}

	public ValidationDocument(String validationDocument) {
		super();
		this.validationDocument = validationDocument;
		this.id = UUID.randomUUID().toString();
	}

	public ValidationDocument() {
		super();
	}

	public String getValidationDocument() {
		return validationDocument;
	}

	public void setValidationDocument(String validationDocument) {
		this.validationDocument = validationDocument;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, validationDocument);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		ValidationDocument other = (ValidationDocument) obj;
		return Objects.equals(id, other.id) && Objects.equals(validationDocument, other.validationDocument);
	}

	@Override
	public String toString() {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", id);
		obj.addProperty("shape", validationDocument);
		return obj.toString();
	}

}
