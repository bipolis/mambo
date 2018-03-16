package org.bipolis.mambo.jaxrs.restconsole;

import static org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE;
import static org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_NAME;
import javax.ws.rs.core.Application;
import org.osgi.service.component.annotations.Component;

@Component(
        service = Application.class,
        property = {JAX_RS_APPLICATION_BASE + "=/" + RestConsoleApp.APPLICATION_NAME,
            JAX_RS_NAME + "=" + RestConsoleApp.APPLICATION_NAME})
//
// @JaxrsName(RestConsoleApplication.APPLICATION_NAME)
// @JaxrsApplicationBase("/" + RestConsoleApplication.APPLICATION_NAME)
// @JaxrsWhiteboardTarget(RestConsoleApplication.TARGET)

public class RestConsoleApp extends Application {

  public static final String APPLICATION_NAME = "RestConsole";

}
