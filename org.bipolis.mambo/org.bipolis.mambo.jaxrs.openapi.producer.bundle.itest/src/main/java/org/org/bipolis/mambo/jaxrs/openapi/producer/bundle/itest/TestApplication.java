package org.org.bipolis.mambo.jaxrs.openapi.producer.bundle.itest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@JaxrsApplicationBase("/test")
@Component(service = Application.class)
@JaxrsResource
public class TestApplication extends Application {

  @Path("echo")
  @GET
  public String getEcho(String echo) {
    return echo;
  }
}
