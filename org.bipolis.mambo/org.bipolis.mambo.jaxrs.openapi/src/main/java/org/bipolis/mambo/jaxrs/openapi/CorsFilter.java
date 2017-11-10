package org.bipolis.mambo.jaxrs.openapi;

import static org.bipolis.mambo.jaxrs.openapi.ApiConstants.ACCESS_CONTROL_ALLOW_HEADERS_HEADER;
import static org.bipolis.mambo.jaxrs.openapi.ApiConstants.ACCESS_CONTROL_ALLOW_METHODS_HEADER;
import static org.bipolis.mambo.jaxrs.openapi.ApiConstants.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER;
import static org.bipolis.mambo.jaxrs.openapi.ApiConstants.ALLOW_METHODS;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.bipolis.mambo.jaxrs.openapi.swagger.SwaggerUi;

//@Component(property = {
//		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=" + OpenApiApplication.APPLICATION_NAME
//				+ ")",
//		JaxRSWhiteboardConstants.JAX_RS_NAME + "=CorsFilter",
//		JaxRSWhiteboardConstants.JAX_RS_EXTENSION + "=true" }, service = { ContainerResponseFilter.class })
public class CorsFilter implements ContainerResponseFilter {

	@Override
	public void filter(final ContainerRequestContext request, final ContainerResponseContext response)
			throws IOException {

		final Set<String> requiredHeaders = new HashSet<>();
		requiredHeaders.addAll(SwaggerUi.CORS_HEADERS);

		final StringBuilder headers = new StringBuilder();
		final Iterator<String> headerIter = requiredHeaders.iterator();

		while (headerIter.hasNext()) {
			headers.append(headerIter.next()).append(headerIter.hasNext() ? ", " : "");
		}

		response.getHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		response.getHeaders().add(ACCESS_CONTROL_ALLOW_METHODS_HEADER, ALLOW_METHODS);
		response.getHeaders().add(ACCESS_CONTROL_ALLOW_HEADERS_HEADER, headers.toString());
	}
}
