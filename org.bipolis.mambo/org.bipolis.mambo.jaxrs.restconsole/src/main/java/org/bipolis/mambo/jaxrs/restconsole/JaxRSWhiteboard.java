package org.bipolis.mambo.jaxrs.restconsole;

import static org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_NAME;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.bipolis.mambo.jaxrs.annotation.mediatype.json.NameBindingJsonProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.json.RequiresJsonProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service = JaxRSWhiteboard.class)

@JaxrsApplicationSelect("(" + JAX_RS_NAME + "=" + RestConsoleApp.APPLICATION_NAME + ")")
@JaxrsName("JaxRSWhiteboard")
@JaxrsResource
@Path("/JaxRSWhiteboard")
public class JaxRSWhiteboard {
  @Reference(cardinality = ReferenceCardinality.OPTIONAL)
  JaxrsServiceRuntime jaxrsServiceRuntime;

  // http://localhost:8080/RestConsole/JaxRSWhiteboard/runtime
  @GET
  @Path("/runtime")
  @Produces("application/json")
  @RequiresJsonProvider
  @NameBindingJsonProvider
  public RuntimeDTO getRuntime() {
    RuntimeDTO runtimeDTO = jaxrsServiceRuntime.getRuntimeDTO();
    return runtimeDTO;

  }

};
