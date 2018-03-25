package org.bipolis.mambo.jaxrs.openapi.itest;

import javax.ws.rs.core.Application;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Component(service = Application.class)

@JaxrsName(OADefinitionFromAnnotationApp.APPLICATION_NAME)
@JaxrsApplicationBase("/" + OADefinitionFromAnnotationApp.APPLICATION_NAME)

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        email = "Annotation-Contact-email",
                        name = "Annotation-Contact-name",
                        url = "Annotation-Contact-url"),
                description = "Annotation-description",
                license = @License(
                        name = "Annotation-License-name",
                        url = "Annotation-License-url"),
                termsOfService = "Annotation-termsOfService",
                title = "Annotation-title",
                version = "Annotation-version"))
public class OADefinitionFromAnnotationApp extends Application {

  public static final String APPLICATION_NAME = "OADefinitionFromAnnotationApp";

}
