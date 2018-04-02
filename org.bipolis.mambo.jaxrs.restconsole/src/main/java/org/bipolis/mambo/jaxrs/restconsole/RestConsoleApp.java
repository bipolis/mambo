package org.bipolis.mambo.jaxrs.restconsole;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = Application.class)

@JaxrsName(RestConsoleApp.APPLICATION_NAME)
@JaxrsApplicationBase("/" + RestConsoleApp.APPLICATION_NAME)

public class RestConsoleApp extends Application {

  public static final String APPLICATION_NAME = "RestConsole";

}
