package org.bipolis.mambo.jaxrs.ext.security.oauth;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.rs.security.oauth2.filters.OAuthRequestFilter;
import org.bipolis.mambo.jaxrs.annotation.security.CapabilitySecurityContextProvider;
import org.bipolis.mambo.jaxrs.annotation.security.NameBindingSecurityProvider;
import org.bipolis.mambo.jaxrs.ext.security.core.AbstractSecurityFilter;
import org.bipolis.mambo.jaxrs.ext.security.core.AuthenticationException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtension;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Provider
@Priority(Priorities.AUTHENTICATION)
@CapabilitySecurityContextProvider
@NameBindingSecurityProvider
@Component(service = ContainerRequestFilter.class)

@JaxrsExtension

@JaxrsName("OAuthAuthenticatorFilter")
public class OAuthAuthenticatorFilter extends AbstractSecurityFilter {


  OAuthRequestFilter oAuthRequestFilter = new OAuthRequestFilter();


  @Override
  public SecurityScheme getSecurityScheme() {

    OAuthFlow oAuthFlow = new OAuthFlow();
    oAuthFlow.setAuthorizationUrl("readfrompropertyFile");

    OAuthFlows oAuthFlows = new OAuthFlows();
    oAuthFlows.setImplicit(oAuthFlow);

    SecurityScheme securityScheme = new SecurityScheme();
    securityScheme.setName("Oauth2");
    securityScheme.setType(SecurityScheme.Type.OAUTH2);
    securityScheme.setIn(SecurityScheme.In.HEADER);
    securityScheme.setFlows(oAuthFlows);

    return securityScheme;
  }

  @Override
  protected SecurityContext authenticate(ContainerRequestContext requestContext)
          throws AuthenticationException {


    oAuthRequestFilter.filter(requestContext);

    return requestContext.getSecurityContext();
  }



}
