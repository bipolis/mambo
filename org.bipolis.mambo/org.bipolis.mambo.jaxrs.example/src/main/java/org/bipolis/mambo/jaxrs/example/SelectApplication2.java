package org.bipolis.mambo.jaxrs.example;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsWhiteboardTarget;

@Component(service = Application.class, property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/" + SelectApplication2.APPLICATION_NAME,
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=" + SelectApplication2.APPLICATION_NAME,
		JaxRSWhiteboardConstants.JAX_RS_WHITEBOARD_TARGET + "=" + SimpleTargetRessource.TARGET })

@JaxrsName(SelectApplication2.APPLICATION_NAME)
@JaxrsApplicationBase("/" + SelectApplication2.APPLICATION_NAME)
@JaxrsWhiteboardTarget(SimpleTargetRessource.TARGET)
public class SelectApplication2 extends Application {

	public static final String APPLICATION_NAME = "SelectApplication2";

}
