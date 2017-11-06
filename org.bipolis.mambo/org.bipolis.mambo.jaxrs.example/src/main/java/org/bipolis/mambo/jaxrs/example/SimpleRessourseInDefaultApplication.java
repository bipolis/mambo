package org.bipolis.mambo.jaxrs.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;

@Component(property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name="
				+ JaxRSWhiteboardConstants.JAX_RS_DEFAULT_APPLICATION + ")",
		JaxRSWhiteboardConstants.JAX_RS_RESOURCE + "=true",
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=Base" }, service = SimpleRessourseInDefaultApplication.class)

@Path("/simple")
public class SimpleRessourseInDefaultApplication {
	@GET
	@Path("/a")
	public String getRuntime() {
		return "a=a";
	}

	@GET
	@Path("/b")
	public Response getR() {
		return Response.ok("b").build();
	}

};
