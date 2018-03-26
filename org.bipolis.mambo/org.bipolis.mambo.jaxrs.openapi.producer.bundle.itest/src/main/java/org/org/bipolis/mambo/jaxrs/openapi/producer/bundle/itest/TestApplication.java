package org.org.bipolis.mambo.jaxrs.openapi.producer.bundle.itest;

import javax.ws.rs.core.Application;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;



@JaxrsApplicationBase("/test")
@Component(service = Application.class)
public class TestApplication extends Application {

}
