package org.bipolis.mambo.jaxrs.openapi.example.basic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@JaxrsApplicationBase("/" + ExampleApplication.APP_NAME)
@Component(service = Application.class)
@JaxrsName(ExampleApplication.APP_NAME)
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
public class ExampleApplication extends Application {


  public final static String APP_NAME = "ExampleApplication";


  @Path("echo")
  @GET
  public String getEcho(String echo) {
    return echo;
  }


}
