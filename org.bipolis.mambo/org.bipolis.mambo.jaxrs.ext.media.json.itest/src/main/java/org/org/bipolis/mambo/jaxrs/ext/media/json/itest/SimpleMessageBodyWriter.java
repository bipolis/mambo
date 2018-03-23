package org.org.bipolis.mambo.jaxrs.ext.media.json.itest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.json.JsonProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtension;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Consumes("*/*")
@Produces("*/")
@Component(service = {javax.ws.rs.ext.MessageBodyWriter.class})
@JaxrsExtension
@JsonProvider
@Provider
@JaxrsName("SimpleMessageBodyWriter")
@SimpleNameBinding
public class SimpleMessageBodyWriter implements MessageBodyWriter<Object> {

  @Override
  public boolean isWriteable(Class<?> arg0,
                             Type arg1,
                             Annotation[] arg2,
                             MediaType arg3) {

    return false;
  }

  @Override
  public void writeTo(Object arg0,
                      Class<?> arg1,
                      Type arg2,
                      Annotation[] arg3,
                      MediaType arg4,
                      MultivaluedMap<String, Object> arg5,
                      OutputStream arg6)
          throws IOException,
          WebApplicationException {

  }

}
