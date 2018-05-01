package org.bipolis.mambo.jaxrs.ext.media.xml.itest;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bipolis.mambo.jaxrs.annotation.mediatype.xml.NameBindingXmlProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.xml.RequiresXmlProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service = TestRessourceXml.class)
@JaxrsApplicationSelect("(" + JaxrsWhiteboardConstants.JAX_RS_NAME + "=" + TestAppXmlNameBind.NAME + ")")
@JaxrsName("r")
@Path("r")
@JaxrsResource

@RequiresXmlProvider
public class TestRessourceXml {
	ExampleDTO example = new ExampleDTO("Text1", Arrays.asList("Element1", "Element2"));

	@NameBindingXmlProvider
	@GET
	@Path("xml")
	@Produces(MediaType.APPLICATION_XML)
	public ExampleDTO getXml() {
		return example;
	}

	@GET
	@Path("non")
	@Produces(MediaType.APPLICATION_XML)
	public ExampleDTO getNon() {
		return example;

	}

}
