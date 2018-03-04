package org.bipolis.mambo.jaxrs.openapi;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

@Component(service = Application.class, property = { JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/api-doc",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=" + OpenApiApplication.APPLICATION_NAME })

// @JaxrsName(OpenApiApplication.APPLICATION_NAME)
// @JaxrsApplicationBase("/" + OpenApiApplication.APPLICATION_NAME)
public class OpenApiApplication extends Application {

	public static final String APPLICATION_NAME = "OpenApiApplication";

	// http://localhost:8080/swagger-ui/index.html
	// http://localhost:8080/api-doc/swagger.json?api=saasas
	// http://localhost:8080/rsinfo
}
