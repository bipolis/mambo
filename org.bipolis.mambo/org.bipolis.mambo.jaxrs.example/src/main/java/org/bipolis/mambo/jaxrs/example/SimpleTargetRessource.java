package org.bipolis.mambo.jaxrs.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(" + JaxRSWhiteboardConstants.JAX_RS_WHITEBOARD_TARGET
				+ "=" + SimpleTargetRessource.TARGET + ")",
		JaxRSWhiteboardConstants.JAX_RS_RESOURCE + "=true",
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=SimpleTargetRessource" }, service = SimpleTargetRessource.class)

@JaxrsApplicationSelect("=(" + JaxRSWhiteboardConstants.JAX_RS_WHITEBOARD_TARGET + "=" + SimpleTargetRessource.TARGET
		+ ")")
@JaxrsName("SimpleTargetRessource")
@JaxrsResource
@Path("/Target")
public class SimpleTargetRessource {

	public static final String TARGET = "ExampleTarget";
	private int i;

	@GET
	@Produces("application/json")
	@Path("count")
	public int getBaseInfo() {
		return i++;

	}
};
