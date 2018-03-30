package org.org.bipolis.mambo.jaxrs.openapi.producer.swagger.itest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@JaxrsApplicationBase("/test")
@Component(service = Application.class)
@JaxrsResource
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
                version = "Annotation-version"),
        externalDocs = @ExternalDocumentation(
                url = "Annotation-ExternalDocumentation-url",
                description = "Annotation-ExternalDocumentation-description"))
public class TestApplication extends Application {

  @Path("echo")
  @GET
  public String getEcho(String echo) {
    return echo;
  }
}
