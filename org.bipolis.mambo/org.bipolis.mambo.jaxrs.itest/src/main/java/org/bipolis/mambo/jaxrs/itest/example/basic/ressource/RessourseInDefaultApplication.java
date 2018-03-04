package org.bipolis.mambo.jaxrs.itest.example.basic.ressource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

@Component(service = RessourseInDefaultApplication.class, property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name="
				+ JaxrsWhiteboardConstants.JAX_RS_DEFAULT_APPLICATION + ")",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=" + RessourseInDefaultApplication.RESSOURCE_NAME })

@Path("/" + RessourseInDefaultApplication.RESSOURCE_NAME)
public class RessourseInDefaultApplication {

	public static final String RESSOURCE_NAME = "RessourseInDefaultApplication";

	@GET
	@Path("/value")
	public String getValue() {
		return getClass().getName();

	}

};
