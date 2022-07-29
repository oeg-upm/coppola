package coppola.exceptions;

import org.apache.http.protocol.HTTP;

import com.google.gson.JsonObject;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

public class Exceptions {

	@SuppressWarnings("rawtypes")
	public static final ExceptionHandler handleException = (Exception exception, Request request,
			Response response) -> {
		response.status(500);
		response.header(HTTP.CONTENT_TYPE, "application/json");
		JsonObject object = new JsonObject();
		object.addProperty("code", 500);
		object.addProperty("message", exception.toString());
		response.body(object.toString());
	};

}
