package coppola.view;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.velocity.app.VelocityEngine;

import com.fasterxml.jackson.databind.ObjectMapper;

import coppola.AppUtils;
import coppola.exceptions.RenderTemplateException;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class VelocityRenderer {

	private VelocityTemplateEngine engine;
	private File viewsDirectory;
	private static final String VIEWS_PATH = "./src/test/resources";
	private ObjectMapper mapper;

	public VelocityRenderer() {
		Properties properties = new Properties();
		properties.setProperty("file.resource.loader.path", VIEWS_PATH);
		engine = new VelocityTemplateEngine(new VelocityEngine(properties));
		mapper = new ObjectMapper();
		viewsDirectory = new File(VIEWS_PATH);
	}


	public String render(Map<String, Object> model, String template) throws RenderTemplateException {
		try {
			if (!fileExists(template))
				throw new RenderTemplateException(
						AppUtils.concat("Template ", template, " is not located under the directory ", VIEWS_PATH));

			return engine.render(new ModelAndView(model, template));
		} catch (Exception e) {
			throw new RenderTemplateException(e.toString());
		}
	}

	private boolean fileExists(String name) {
		return Arrays.asList(viewsDirectory.listFiles()).parallelStream()
				.anyMatch(file -> file.getName().endsWith(name));
	}


	public Map<String, Object> toModel(Object object) {
		@SuppressWarnings("unchecked")
		Map<String, Object> model = mapper.convertValue(object, Map.class);
		return model;
	}


	public String render(Object object, String template) throws RenderTemplateException {
		return render(toModel(object), template);
	}


	public Map<String, Object> toModel(List<?> object, String variable) {
		Map<String,Object> model = new HashMap<>();
		List<Object> values = object.parallelStream().map(elem -> toModel(elem)).collect(Collectors.toList());
		model.put(variable, values);
		return model;
	}
}