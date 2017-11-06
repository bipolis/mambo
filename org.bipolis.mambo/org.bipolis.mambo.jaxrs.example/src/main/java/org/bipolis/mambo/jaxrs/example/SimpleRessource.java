package org.bipolis.mambo.jaxrs.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(" + JaxRSWhiteboardConstants.JAX_RS_NAME + "="
				+ SimpleApplication1.APPLICATION_NAME + ")",
		JaxRSWhiteboardConstants.JAX_RS_RESOURCE + "=true",
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=SimpleRessource" }, service = SimpleRessource.class)

@JaxrsApplicationSelect("=(" + JaxRSWhiteboardConstants.JAX_RS_NAME + "=" + SimpleApplication1.APPLICATION_NAME + ")")
@JaxrsName("SimpleRessource")
@JaxrsResource
@Path("/simple")
public class SimpleRessource {

	private int i;

	@GET
	@Path("count")
	public String getBaseInfo() {
		return String.valueOf(i++);

	}
};
