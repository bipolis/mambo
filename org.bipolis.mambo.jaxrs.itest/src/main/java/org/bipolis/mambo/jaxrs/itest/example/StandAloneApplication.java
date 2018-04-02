package org.bipolis.mambo.jaxrs.itest.example;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

@Component(
        service = Application.class,
        property = {
            JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/"
                    + StandAloneApplication.APPLICATION_NAME,
            JaxrsWhiteboardConstants.JAX_RS_NAME + "=" + StandAloneApplication.APPLICATION_NAME})

public class StandAloneApplication extends Application {

  public static final String APPLICATION_NAME = "StandAloneApplication";

}
