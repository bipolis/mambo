package org.bipolis.mambo.jaxrs.itest.example.basic.ressource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;

@Component(service = RessourseInDefaultApplication.class, property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name="
				+ JaxRSWhiteboardConstants.JAX_RS_DEFAULT_APPLICATION + ")",
		JaxRSWhiteboardConstants.JAX_RS_RESOURCE + "=true",
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=" + RessourseInDefaultApplication.RESSOURCE_NAME })

@Path("/" + RessourseInDefaultApplication.RESSOURCE_NAME)
public class RessourseInDefaultApplication {

	public static final String RESSOURCE_NAME = "RessourseInDefaultApplication";

	@GET
	@Path("/value")
	public String getValue() {
		return getClass().getName();

	}

};
