package org.bipolis.mambo.jaxrs.openapi;

import java.util.Set;

import javax.servlet.ServletConfig;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxRSWhiteboardConstants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import io.swagger.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.jaxrs2.integration.ServletConfigContextUtils;
import io.swagger.jaxrs2.integration.resources.BaseOpenApiResource;
import io.swagger.oas.integration.api.OpenAPIConfiguration;
import io.swagger.oas.integration.api.OpenApiContext;
import io.swagger.oas.models.OpenAPI;

@Component(property = {
		JaxRSWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name="
				+ JaxRSWhiteboardConstants.JAX_RS_DEFAULT_APPLICATION + ")",
		JaxRSWhiteboardConstants.JAX_RS_RESOURCE + "=true",
		JaxRSWhiteboardConstants.JAX_RS_NAME + "=OpenApi" }, service = OpenApiResource.class)

@Path("/openapi")
public class OpenApiResource extends BaseOpenApiResource {
	@Context
	ServletConfig config;

	@Context
	Application app;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, "application/yaml" })
	@Path("/openapi.{type:json|yaml}")
	public Response getOpenApi(@Context HttpHeaders headers, @Context UriInfo uriInfo, @PathParam("type") String type)
			throws Exception {
		String ctxId = ServletConfigContextUtils.getContextIdFromServletConfig(config);
		OpenApiContext ctx = new JaxrsOpenApiContextBuilder().servletConfig(config).application(app)
				.resourcePackages(resourcePackages).configLocation(configLocation)
				.openApiConfiguration(openApiConfiguration).ctxId(null).buildContext(true);
		OpenAPI oas = ctx.read();

		if (oas == null) {
			return Response.status(404).build();
		}

		if (type != null && type.trim().equalsIgnoreCase("yaml")) {

			try {
				String s = new YAMLMapper().writer().writeValueAsString(oas);
				return Response.status(Response.Status.OK).entity(s).type("application/yaml").build();
			} catch (JsonProcessingException e) {

				return Response.status(400).build();

			}

		} else {
			return Response.status(Response.Status.OK).entity(oas).type(MediaType.APPLICATION_JSON_TYPE).build();
		}

	}

	protected String configLocation;

	public String getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}

	public BaseOpenApiResource configLocation(String configLocation) {
		setConfigLocation(configLocation);
		return this;
	}

	public BaseOpenApiResource resourcePackages(Set<String> resourcePackages) {
		setResourcePackages(resourcePackages);
		return this;
	}

	public BaseOpenApiResource openApiConfiguration(OpenAPIConfiguration openApiConfiguration) {
		setOpenApiConfiguration(openApiConfiguration);
		return this;
	}

};
