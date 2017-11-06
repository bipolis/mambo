package org.bipolis.mambo.jaxrs.example;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = Application.class, property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/" + SimpleApplication1.APPLICATION_NAME,
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=" + SimpleApplication1.APPLICATION_NAME })

@JaxrsName(SimpleApplication1.APPLICATION_NAME)
@JaxrsApplicationBase("/" + SimpleApplication1.APPLICATION_NAME)
public class SimpleApplication1 extends Application {

	public static final String APPLICATION_NAME = "SimpleApplication1";

}
