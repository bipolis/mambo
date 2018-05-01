package org.bipolis.mambo.jaxrs.itest.example.basic.application;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = Application.class)

@JaxrsName(BasicApplicationA.APPLICATION_NAME)
@JaxrsApplicationBase("/" + BasicApplicationA.APPLICATION_NAME)
public class BasicApplicationA extends Application {

	public static final String APPLICATION_NAME = "BasicApplicationA";

}
