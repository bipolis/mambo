package org.bipolis.mambo.jaxrs.itest.example.basic.application;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;

@Component(service = Application.class, property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/" + BasicApplication.APPLICATION_NAME,
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=" + BasicApplication.APPLICATION_NAME })

public class BasicApplication extends Application {

	public static final String APPLICATION_NAME = "BasicApplication";

}
