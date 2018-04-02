package org.bipolis.mambo.jaxrs.ext.media.xml;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.bipolis.mambo.jaxrs.annotation.mediatype.xml.CapabilityXmlProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtension;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

@Consumes("*/*")
@Produces("*/")
@Component(
        service = {OSGiJacksonJaxbXmlProvider.class, javax.ws.rs.ext.MessageBodyReader.class,
            javax.ws.rs.ext.MessageBodyWriter.class})
@JaxrsExtension
@Provider
@JaxrsName("OSGiJacksonJaxbXmlProvider")

@CapabilityXmlProvider
public class OSGiJacksonJaxbXmlProvider extends JacksonJaxbXMLProvider {
  JaxbAnnotationIntrospector catchDependency;// needed as resolvinghelper


}
