package org.org.bipolis.mambo.jaxrs.openapi.basic;

import io.swagger.v3.oas.models.security.SecurityScheme;

public interface SecuritySchemeProvider {

  SecurityScheme getSecurityScheme();

}
