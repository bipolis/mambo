package org.org.bipolis.mambo.jaxrs.openapi.rs;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = Application.class)

@JaxrsName(OpenApiApplication.APPLICATION_NAME)
@JaxrsApplicationBase("/openapi")
public class OpenApiApplication extends Application {

  public static final String APPLICATION_NAME = "OpenApiApplication";

}
