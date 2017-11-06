package org.bipolis.mambo.jaxrs.restconsole;
import static org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants.JAX_RS_APPLICATION_BASE;
import static org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants.JAX_RS_NAME;
import static org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants.JAX_RS_WHITEBOARD_TARGET;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;

@Component(service = Application.class, property = {
		JAX_RS_APPLICATION_BASE + "=/" + RestConsoleApp.APPLICATION_NAME,
		JAX_RS_NAME + "=" + RestConsoleApp.APPLICATION_NAME ,
		JAX_RS_WHITEBOARD_TARGET + "=" + RestConsoleApp.TARGET})
//
//@JaxrsName(RestConsoleApplication.APPLICATION_NAME)
//@JaxrsApplicationBase("/" + RestConsoleApplication.APPLICATION_NAME)
//@JaxrsWhiteboardTarget(RestConsoleApplication.TARGET)
public class RestConsoleApp extends Application {

	public static final String APPLICATION_NAME = "RestConsole";
	public static final String TARGET = "RestConsole";

}
