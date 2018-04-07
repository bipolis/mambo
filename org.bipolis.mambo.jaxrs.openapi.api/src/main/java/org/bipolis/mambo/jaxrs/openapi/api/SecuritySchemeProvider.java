package org.bipolis.mambo.jaxrs.openapi.api;

import io.swagger.v3.oas.models.security.SecurityScheme;

public interface SecuritySchemeProvider {

  SecurityScheme getSecurityScheme();

}
