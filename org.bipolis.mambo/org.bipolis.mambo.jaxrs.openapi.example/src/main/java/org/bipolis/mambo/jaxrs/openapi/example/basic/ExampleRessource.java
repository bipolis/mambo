package org.bipolis.mambo.jaxrs.openapi.example.basic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(
        property = {
            JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name="
                    + ApplicationExample.APPLICATION_NAME + ")",
            JaxrsWhiteboardConstants.JAX_RS_NAME + "=ExampleRessource",
            JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true"},
        service = {ExampleRessource.class})

@JaxrsApplicationSelect("(osgi.jaxrs.name=" + ApplicationExample.APPLICATION_NAME + ")")
@JaxrsName("ExampleRessource")
@JaxrsResource
public class ExampleRessource {

  int i;

  @GET
  @Path("count")
  @Produces("application/json")
  public int getNumber() {

    return i++;
  }

}
