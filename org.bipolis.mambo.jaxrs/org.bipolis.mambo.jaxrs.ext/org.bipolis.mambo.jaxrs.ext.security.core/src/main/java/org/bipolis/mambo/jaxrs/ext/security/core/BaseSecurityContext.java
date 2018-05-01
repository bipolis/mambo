package org.bipolis.mambo.jaxrs.ext.security.core;

import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

import org.osgi.service.useradmin.Authorization;

public class BaseSecurityContext implements SecurityContext {

  private Authorization authorization;
  private String authenticationScheme;
  private ContainerRequestContext filterContext;

  public BaseSecurityContext(final Authorization authorization,
          String authenticationScheme,
          ContainerRequestContext filterContext) {
    this.authorization = authorization;
    this.authenticationScheme = authenticationScheme;
    this.filterContext = filterContext;
  }

  @Override
  public Principal getUserPrincipal() {
    return new Principal() {

      @Override
      public String getName() {
        return authorization.getName();
      }
    };
  }

  @Override
  public boolean isUserInRole(String role) {

    return authorization.hasRole(role);
  }

  @Override
  public boolean isSecure() {
    return "https".equals(filterContext.getUriInfo()
                                       .getRequestUri()
                                       .getScheme());
  }

  @Override
  public String getAuthenticationScheme() {

    return authenticationScheme;
  }

}
