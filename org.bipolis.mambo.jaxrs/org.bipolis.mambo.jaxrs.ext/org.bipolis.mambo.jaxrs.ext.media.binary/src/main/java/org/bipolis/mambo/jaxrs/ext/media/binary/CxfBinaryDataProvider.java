package org.bipolis.mambo.jaxrs.ext.media.binary;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.bipolis.mambo.jaxrs.annotation.mediatype.binary.CapabilityBinaryDataProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtension;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Consumes("*/*")
@Produces("*/*")
@Component(service = { CxfBinaryDataProvider.class, javax.ws.rs.ext.MessageBodyReader.class,
		javax.ws.rs.ext.MessageBodyWriter.class })
@JaxrsExtension
@CapabilityBinaryDataProvider
@Provider
@JaxrsName("CxfBinaryDataProvider")
public class CxfBinaryDataProvider<T> extends org.apache.cxf.jaxrs.provider.BinaryDataProvider<T> {

}
