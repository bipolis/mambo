package org.bipolis.mambo.jaxrs.ext.media.json.itest;

import java.util.Arrays;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bipolis.mambo.jaxrs.annotation.mediatype.json.RequiresJsonProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service = TestRessourceJson.class)
@JaxrsApplicationSelect("(" + JaxrsWhiteboardConstants.JAX_RS_NAME + "=a)")
@JaxrsName("r")
@Path("r")
@JaxrsResource
public class TestRessourceJson {



  @RequiresJsonProvider
  @GET
  @Path("json")
  @Produces(MediaType.APPLICATION_JSON)
  public ExampleDTO getJSon() {
    return new ExampleDTO("Text1", Arrays.asList("Element1", "Element2"));

  }

  @GET
  @Path("nojson")
  @Produces(MediaType.APPLICATION_JSON)
  public ExampleDTO getNoJSon() {
    return new ExampleDTO("Text1", Arrays.asList("Element1", "Element2"));

  }


  @GET
  @SimpleNameBinding
  @Path("simpleNameBindingTest")
  public ExampleDTO getNameBindingTest() {
    return new ExampleDTO("Text1", Arrays.asList("Element1", "Element2"));

  }
}
