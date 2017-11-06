package org.bipolis.mambo.jaxrs.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;

@Component(property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name="
				+ JaxRSWhiteboardConstants.JAX_RS_DEFAULT_APPLICATION + ")",
//		JaxRSWhiteboardConstants.JAX_RS_RESOURCE + "=true",
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=Base" }, service = Base.class)

@Path("/a")
public class Base {
	@GET
	@Path("/b")
	@Produces("application/json")
	public String getRuntime() {
		return "c";
	}
	
	
	

	@GET
	@Path("/c")
	@Produces("application/json")
	public Response getR() {
		return Response.ok("ss").build();
	}

};
