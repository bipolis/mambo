package org.bipolis.mambo.jaxrs.ext.security.basicauth;

import java.nio.charset.Charset;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import org.bipolis.mambo.jaxrs.annotation.security.CapabilitySecurityContextProvider;
import org.bipolis.mambo.jaxrs.annotation.security.NameBindingSecurityProvider;
import org.bipolis.mambo.jaxrs.ext.security.core.AbstractSecurityFilter;
import org.bipolis.mambo.jaxrs.ext.security.core.AuthenticationException;
import org.bipolis.mambo.jaxrs.ext.security.core.BaseSecurityContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtension;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.useradmin.Authorization;
import org.osgi.service.useradmin.User;
import org.osgi.service.useradmin.UserAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Component(service = ContainerRequestFilter.class)
@Provider
@JaxrsExtension
@CapabilitySecurityContextProvider
@NameBindingSecurityProvider
@SecurityScheme(name = "BasicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
@JaxrsName("BaseAuthAuthenticator")
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthAuthenticator extends AbstractSecurityFilter {

	private static Logger logger = LoggerFactory.getLogger(BasicAuthAuthenticator.class);

	@Reference
	UserAdmin userAdmin;

	protected BaseSecurityContext authenticate(ContainerRequestContext filterContext) throws AuthenticationException {

		// Extract authentication credentials
		String authentication = filterContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authentication == null) {
			throw new AuthenticationException("Authentication credentials are required");
		}

		if (!authentication.startsWith("Basic ")) {
			throw new AuthenticationException("Basic Authentication credentials are required");
		}

		authentication = authentication.substring("Basic ".length());
		String[] values = new String(DatatypeConverter.parseBase64Binary(authentication), Charset.forName("ASCII"))
				.split(":");
		if (values.length < 2) {
			throw new WebApplicationException(400);
			// "Invalid syntax for username and password"
		}
		String username = values[0];
		String password = values[1];
		if ((username == null) || (password == null)) {
			throw new WebApplicationException(400);

		}

		if (userAdmin == null) {
			throw new AuthenticationException("No Authentication Service available");
		}

		User user = userAdmin.getUser("username", username);
		if (user == null || !user.hasCredential("password", password)) {
			// or Anonymous User and Go?
			throw new AuthenticationException("Wrong user or password");
		}

		Authorization authorization = userAdmin.getAuthorization(user);

		return new BaseSecurityContext(authorization, SecurityContext.BASIC_AUTH, filterContext);
	}

	@Override
	public io.swagger.v3.oas.models.security.SecurityScheme getSecurityScheme() {
		// done with annotation
		return null;
	}

}
