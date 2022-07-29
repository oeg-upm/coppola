package coppola.exceptions;

import org.apache.http.protocol.HTTP;

import com.google.gson.JsonObject;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

public class RenderTemplateException extends RuntimeException  {
	private static final long serialVersionUID = -7982702000551927171L;

	public RenderTemplateException(String msg) {
		super(msg);
	}

	@SuppressWarnings("rawtypes")
	public static final ExceptionHandler handle = (Exception exception, Request request, Response response) -> {
		response.status(400);
		response.header(HTTP.CONTENT_TYPE, "application/json");
		JsonObject object = new JsonObject();
		object.addProperty("code", 400);
		object.addProperty("message", exception.toString());
		response.body(object.toString());
	};
}
