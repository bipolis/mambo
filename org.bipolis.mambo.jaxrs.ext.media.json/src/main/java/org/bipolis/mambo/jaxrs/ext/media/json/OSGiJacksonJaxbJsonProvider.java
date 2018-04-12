package org.bipolis.mambo.jaxrs.ext.media.json;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.bipolis.mambo.jaxrs.annotation.mediatype.json.CapabilityJsonProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.json.NameBindingJsonProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtension;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

@Consumes("*/*")
@Produces("*/")
@Component(
        service = {OSGiJacksonJaxbJsonProvider.class,
            javax.ws.rs.ext.MessageBodyReader.class,
            javax.ws.rs.ext.MessageBodyWriter.class})
@JaxrsExtension
@Provider
@JaxrsName("OSGiJacksonJaxbJsonProvider")

@CapabilityJsonProvider
@NameBindingJsonProvider
public class OSGiJacksonJaxbJsonProvider extends JacksonJaxbJsonProvider {

  private static ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.setSerializationInclusion(Include.NON_EMPTY); 
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  public OSGiJacksonJaxbJsonProvider() {
    super(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
  }

  JaxbAnnotationIntrospector catchDependency;// needed as resolvinghelper

}
