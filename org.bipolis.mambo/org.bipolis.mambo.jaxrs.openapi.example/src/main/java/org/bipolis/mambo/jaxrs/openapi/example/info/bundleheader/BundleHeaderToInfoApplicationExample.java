package org.bipolis.mambo.jaxrs.openapi.example.info.bundleheader;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = Application.class, property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/" + BundleHeaderToInfoApplicationExample.APPLICATION_NAME,
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=" + BundleHeaderToInfoApplicationExample.APPLICATION_NAME })

@JaxrsName(BundleHeaderToInfoApplicationExample.APPLICATION_NAME)
@JaxrsApplicationBase("/" + BundleHeaderToInfoApplicationExample.APPLICATION_NAME)
public class BundleHeaderToInfoApplicationExample extends Application {

	public static final String APPLICATION_NAME = "BundleHeaderToInfoApplicationExample";

}
