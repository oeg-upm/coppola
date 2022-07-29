package coppola;


import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFileLocation;

import com.github.jsonldjava.shaded.com.google.common.collect.Maps;

import coppola.controller.Controller;
import coppola.exceptions.Exceptions;
import coppola.exceptions.InvalidRequestException;
import coppola.exceptions.RenderTemplateException;
import coppola.exceptions.RepositoryException;
import coppola.view.VelocityRenderer;

public class App {

	private static VelocityRenderer render = new VelocityRenderer();
	  @SuppressWarnings("unchecked")
	public static void main( String[] args ) {
	    	setUp();
	    	staticFileLocation("/");
	    	get("/", (req, res) -> {return render.render(Maps.newHashMap(), "index.html");});
			get("", (req, res) -> {return render.render(Maps.newHashMap(), "index.html");});

	    	path("/api", () -> {
	    		get("/", Controller.list);
	    		get("", Controller.list);
		    	get("/:id", Controller.get);
		    	post("/:id", Controller.apply);
		    	put("/:id", Controller.create);
		    	delete("/:id", Controller.remove);
	    	 });


	    	 exception(InvalidRequestException.class, InvalidRequestException.handle);
	    	 exception(RenderTemplateException.class, RenderTemplateException.handle);
	    	 exception(RepositoryException.class, RepositoryException.handle);
	    	 exception(Exception.class, Exceptions.handleException);
	  }

	    public static void setUp() {

	    }
}
