package org.bipolis.mambo.jaxrs.ext.security.core;

import java.lang.reflect.Method;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.bipolis.mambo.jaxrs.openapi.api.SecuritySchemeProvider;

public abstract class AbstractSecurityFilter
		implements javax.ws.rs.container.ContainerRequestFilter, SecuritySchemeProvider {

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) {

		Class<?> clazz = resourceInfo.getResourceClass();
		Method method = resourceInfo.getResourceMethod();

		boolean classPermittAll = clazz.isAnnotationPresent(PermitAll.class);
		boolean methodPermittAll = method.isAnnotationPresent(PermitAll.class);
		boolean classDenyAll = clazz.isAnnotationPresent(DenyAll.class);
		boolean methodDenyAll = method.isAnnotationPresent(DenyAll.class);
		RolesAllowed classRolesAllowedRoles = clazz.getAnnotation(RolesAllowed.class);
		RolesAllowed methodRolesAllowedRoles = method.getAnnotation(RolesAllowed.class);

		// 1. Method over Class
		// 2. Deny over Role over Permit
		// 3. If Nothing then Auth
		// 4. no Roles same Auth

		if (methodDenyAll) {
			responseForbidden(requestContext);
		} else if (methodRolesAllowedRoles != null) {

			authFilter(requestContext, methodRolesAllowedRoles);
			return;
		} else if (methodPermittAll) {
			return;
		} else if (classDenyAll) {
			responseForbidden(requestContext);
		} else if (classRolesAllowedRoles != null) {
			authFilter(requestContext, classRolesAllowedRoles);
		} else if (classPermittAll) {
			return;
		} else {
			authFilter(requestContext, null);
		}
	}

	private void responseForbidden(ContainerRequestContext requestContext) {
		requestContext
				.abortWith(Response.status(Response.Status.FORBIDDEN).entity("Access blocked for all users").build());
	}

	private void authFilter(ContainerRequestContext requestContext, RolesAllowed methodRolesAllowedRoles) {
		try {

			SecurityContext securityContext = authenticate(requestContext);
			requestContext.setSecurityContext(securityContext);
			boolean annotatedRoleOk = false;
			if (methodRolesAllowedRoles == null) {
				annotatedRoleOk = true;
			} else {
				for (final String role : methodRolesAllowedRoles.value()) {
					if (securityContext.isUserInRole(role)) {
						annotatedRoleOk = true;
						break;
					}
				}
			}
			if (!annotatedRoleOk) {
				throw new AuthenticationException("No matching roles");
			}

			// TODO: Roles over Path

		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build());
		}

	}

	protected abstract SecurityContext authenticate(ContainerRequestContext requestContext)
			throws AuthenticationException;

}
