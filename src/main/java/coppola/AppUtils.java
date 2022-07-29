package coppola;

import spark.Request;

public class AppUtils {

	private static final String ACCEPT_HEADER = "Accept";
	private static final String ACCEPT_HEADER_HTML = "text/html";
	private static final String ACCEPT_HEADER_JSON = "application/json";

	public static boolean shouldReturnHtml(Request request) {
	    String accept = request.headers(ACCEPT_HEADER);
	    return accept != null && accept.contains(ACCEPT_HEADER_HTML);
	}

	public static boolean shouldReturnJson(Request request) {
	    String accept = request.headers(ACCEPT_HEADER);
	    return accept != null && accept.contains(ACCEPT_HEADER_JSON);
	}

	public static String concat(String ... args) {
		StringBuilder builder = new StringBuilder();
		for (String arg : args) {
			builder.append(arg);
		}
		return builder.toString();
	}
}
