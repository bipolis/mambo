package org.org.bipolis.mambo.jaxrs.ext.media.yaml.itest;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.bipolis.mambo.jaxrs.annotation.mediatype.yaml.NameBindingYamlProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.yaml.RequiresYamlProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service = TestRessourceYaml.class)
@JaxrsApplicationSelect("(" + JaxrsWhiteboardConstants.JAX_RS_NAME + "=" + TestAppYamlNameBind.NAME
        + ")")
@JaxrsName("r")
@Path("r")
@JaxrsResource

@RequiresYamlProvider
public class TestRessourceYaml {
  ExampleDTO example = new ExampleDTO("Text1", Arrays.asList("Element1", "Element2"));

  @NameBindingYamlProvider
  @GET
  @Path("yaml")
  @Produces("application/yaml")
  public ExampleDTO getYaml() {
    return example;
  }

  @GET
  @Path("non")
  @Produces("application/yaml")
  public ExampleDTO getNon() {
    return example;

  }

}
