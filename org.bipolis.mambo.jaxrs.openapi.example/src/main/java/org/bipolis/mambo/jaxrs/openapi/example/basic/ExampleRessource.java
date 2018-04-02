package org.bipolis.mambo.jaxrs.openapi.example.basic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service = ExampleRessource.class)
@JaxrsName("ExampleRessource")
@JaxrsResource
@JaxrsApplicationSelect("(" + JaxrsWhiteboardConstants.JAX_RS_NAME + "="
        + ExampleApplication.APP_NAME + ")")
@Path("/" + ExampleRessource.NAME)
public class ExampleRessource {
  public final static String NAME = "TestRessource";

  @GET
  @Path("/upper")
  public String upper(String text) {
    return text.toUpperCase();
  }

  @GET
  @Path("/lowerAndFilter")
  @ExampleNameBinding
  public String lowerAndFilter(String text) {
    return text.toLowerCase();
  }
}
