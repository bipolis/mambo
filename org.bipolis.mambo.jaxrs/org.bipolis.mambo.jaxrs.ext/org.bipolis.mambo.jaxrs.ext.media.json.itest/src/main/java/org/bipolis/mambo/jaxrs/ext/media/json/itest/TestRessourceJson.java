package org.bipolis.mambo.jaxrs.ext.media.json.itest;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bipolis.mambo.jaxrs.annotation.mediatype.json.NameBindingJsonProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.json.RequiresJsonProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service = TestRessourceJson.class)
@JaxrsApplicationSelect("(" + JaxrsWhiteboardConstants.JAX_RS_NAME + "=" + TestAppJsonNameBind.NAME + ")")
@JaxrsName("r")
@Path("r")
@JaxrsResource

@RequiresJsonProvider
public class TestRessourceJson {
	ExampleDTO example = new ExampleDTO("Text1", Arrays.asList("Element1", "Element2"));

	@NameBindingJsonProvider
	@GET
	@Path("json")
	@Produces(MediaType.APPLICATION_JSON)
	public ExampleDTO getJSon() {
		return example;

	}

	@GET
	@Path("non")
	@Produces(MediaType.APPLICATION_JSON)
	public ExampleDTO getNon() {
		return example;

	}

}
