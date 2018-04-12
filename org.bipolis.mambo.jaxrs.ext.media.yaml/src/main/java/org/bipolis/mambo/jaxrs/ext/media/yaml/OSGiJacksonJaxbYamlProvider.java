package org.bipolis.mambo.jaxrs.ext.media.yaml;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.bipolis.mambo.jaxrs.annotation.mediatype.yaml.CapabilityYamlProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.yaml.RequiresYamlProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtension;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.yaml.JacksonJaxbYAMLProvider;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

@Consumes("*/*")
@Produces("*/")
@Component(
        service = {OSGiJacksonJaxbYamlProvider.class,
            javax.ws.rs.ext.MessageBodyReader.class,
            javax.ws.rs.ext.MessageBodyWriter.class})
@JaxrsExtension
@RequiresYamlProvider
@Provider
@JaxrsName("JacksonJaxbJsonProvider")
@CapabilityYamlProvider
public class OSGiJacksonJaxbYamlProvider extends JacksonJaxbYAMLProvider {
  JaxbAnnotationIntrospector catchDependency;

  private static YAMLMapper mapper = new YAMLMapper();

  static {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.setSerializationInclusion(Include.NON_EMPTY);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  public OSGiJacksonJaxbYamlProvider() {
    super(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
  }

}
