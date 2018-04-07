package org.bipolis.mambo.jaxrs.openapi.example;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtension;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@Component(service = ReaderInterceptor.class)
@JaxrsName(ExampleExtension.EXTENSION_NAME)
@JaxrsExtension
@ExampleNameBinding

@SecuritySchemes({
    @SecurityScheme(name = "BasicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")})
public class ExampleExtension implements ReaderInterceptor {

  public static final String EXTENSION_NAME = "ExampleExtension";

  @Override
  public Object aroundReadFrom(ReaderInterceptorContext arg0)
          throws IOException,
          WebApplicationException {

    return arg0;
  }

}
