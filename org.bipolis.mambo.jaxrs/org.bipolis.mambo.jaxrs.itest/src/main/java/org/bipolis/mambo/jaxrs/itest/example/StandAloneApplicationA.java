package org.bipolis.mambo.jaxrs.itest.example;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = Application.class)

@JaxrsName(StandAloneApplicationA.APPLICATION_NAME)
@JaxrsApplicationBase("/" + StandAloneApplicationA.APPLICATION_NAME)

public class StandAloneApplicationA extends Application {

	public static final String APPLICATION_NAME = "StandAloneApplicationA";

}
