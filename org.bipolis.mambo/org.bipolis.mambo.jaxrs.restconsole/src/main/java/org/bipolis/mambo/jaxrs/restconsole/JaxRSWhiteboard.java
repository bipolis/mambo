package org.bipolis.mambo.jaxrs.restconsole;

import static org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants.JAX_RS_APPLICATION_SELECT;
import static org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants.JAX_RS_NAME;
import static org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants.JAX_RS_RESOURCE;
import static org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants.JAX_RS_WHITEBOARD_TARGET;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.jaxrs.runtime.JaxRSServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;

@Component(service = JaxRSWhiteboard.class, property = {
		JAX_RS_APPLICATION_SELECT + "=(" + JAX_RS_WHITEBOARD_TARGET + "=" + RestConsoleApp.APPLICATION_NAME + ")",
		JAX_RS_RESOURCE + "=true", JAX_RS_NAME + "=JaxRSWhiteboard" })
//
//@JaxrsApplicationSelect("(+JAX_RS_WHITEBOARD_TARGET+=" + RestConsoleApplication.APPLICATION_NAME + ")")
//@JaxrsName("JaxRSWhiteboard")
//@JaxrsResource
@Path("/JaxRSWhiteboard")
public class JaxRSWhiteboard {
	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	JaxRSServiceRuntime jaxRSServiceRuntime;

	// http://localhost:8080/RestConsole/JaxRSWhiteboard/runtime
	@GET
	@Path("/runtime")
	@Produces("application/json")
	public String getRuntime() {
		RuntimeDTO runtimeDTO = jaxRSServiceRuntime.getRuntimeDTO();
		return runtimeDTO.toString();

	}

};
