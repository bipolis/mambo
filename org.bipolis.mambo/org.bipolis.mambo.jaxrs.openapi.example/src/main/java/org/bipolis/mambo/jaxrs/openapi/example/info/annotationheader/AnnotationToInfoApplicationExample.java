package org.bipolis.mambo.jaxrs.openapi.example.info.annotationheader;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

import io.swagger.oas.annotations.info.Contact;
import io.swagger.oas.annotations.info.Info;
import io.swagger.oas.annotations.info.License;

@Component(service = Application.class, property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/" + AnnotationToInfoApplicationExample.APPLICATION_NAME,
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=" + AnnotationToInfoApplicationExample.APPLICATION_NAME })

@JaxrsName(AnnotationToInfoApplicationExample.APPLICATION_NAME)
@JaxrsApplicationBase("/" + AnnotationToInfoApplicationExample.APPLICATION_NAME)

@Info(contact = @Contact(email = "Annotation-Contact-email", name = "Annotation-Contact-name", url = "Annotation-Contact-url"), description = "Annotation-description", license = @License(name = "Annotation-License-name", url = "Annotation-License-url"), termsOfService = "Annotation-termsOfService", title = "Annotation-title", version = "Annotation-version")
public class AnnotationToInfoApplicationExample extends Application {

	public static final String APPLICATION_NAME = "AnnotationToInfoApplicationExample";

}
