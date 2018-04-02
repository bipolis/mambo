package org.bipolis.mambo.jaxrs.ext.media.json;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.bipolis.mambo.jaxrs.annotation.mediatype.json.CapabilityJsonProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.json.NameBindingJsonProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtension;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

@Consumes("*/*")
@Produces("*/")
@Component(
        service = {OSGiJacksonJaxbJsonProvider.class, javax.ws.rs.ext.MessageBodyReader.class,
            javax.ws.rs.ext.MessageBodyWriter.class})
@JaxrsExtension
@Provider
@JaxrsName("OSGiJacksonJaxbJsonProvider")

@CapabilityJsonProvider
@NameBindingJsonProvider
public class OSGiJacksonJaxbJsonProvider extends JacksonJaxbJsonProvider {
  JaxbAnnotationIntrospector catchDependency;// needed as resolvinghelper


}
