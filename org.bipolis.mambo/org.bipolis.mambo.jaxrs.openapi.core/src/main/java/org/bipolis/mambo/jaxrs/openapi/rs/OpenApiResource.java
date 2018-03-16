package org.bipolis.mambo.jaxrs.openapi.rs;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.bipolis.mambo.jaxrs.openapi.api.OpenApiService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;
import org.osgi.service.log.Logger;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Component(service = OpenApiResource.class)
@JaxrsName("OpenApiResource")
@JaxrsResource
@JaxrsApplicationSelect("(" + JaxrsWhiteboardConstants.JAX_RS_NAME + "="
        + OpenApiApplication.APPLICATION_NAME + ")")
@Path(OpenApiResource.BASEPATH)
@PermitAll
public class OpenApiResource {
  protected static final String BASEPATH = "/doc";

  private static final String SWAGGER_UI_PATH = "/static/swaggerui";

  public static <S> S getServiceByServiceID(final BundleContext bundleContext,
                                            final Class<S> clazz,
                                            final long serviceId)
          throws InvalidSyntaxException {

    final Collection<ServiceReference<S>> srs =
            bundleContext.getServiceReferences(clazz, "(service.id=" + serviceId + ")");
    final ServiceReference<S> sr = srs.iterator()
                                      .next();
    final S service = bundleContext.getService(sr);

    return service;
  }


  @Context
  private UriInfo uri;
  @Reference
  private Logger logger;
  @Reference
  private OpenApiService openApiService;
  @Reference
  private JaxrsServiceRuntime jaxrsServiceRuntime;


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/application")
  public String[] getApplication() {

    final List<String> applications = new ArrayList<>();

    final RuntimeDTO runtimeDTO = jaxrsServiceRuntime.getRuntimeDTO();

    if (runtimeDTO.applicationDTOs != null) {
      for (final ApplicationDTO applicationDTO : runtimeDTO.applicationDTOs) {
        applications.add(applicationDTO.name);
      }
    }

    return applications.toArray(new String[] {});
  }

  @GET
  @Produces("text/html")
  public String getApplicationsUI(@Context final UriInfo uriInfo) {

    final URI baseurl = uriInfo.getBaseUri();

    final String swaggeruri =
            baseurl.getScheme() + "://" + baseurl.getAuthority() + SWAGGER_UI_PATH;

    final StringBuilder htmlBuilder = new StringBuilder();

    htmlBuilder.append("<html>");

    for (final String app : getApplication()) {

      htmlBuilder.append("<a href=" + swaggeruri + "/index.html?url=" + uri.getBaseUri()
              + OpenApiResource.BASEPATH.replace("/", "") + "/application/" + app + "/openapi.yaml>"
              + app + "</a>");
      htmlBuilder.append("<br>");
    }

    htmlBuilder.append("</html>");
    return htmlBuilder.toString();
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON, "application/yaml"})
  @Path("/application/{application}/openapi.{type:json|yaml}")
  public Response getOpenApi(@Context final HttpHeaders headers,
                             @Context final UriInfo uriInfo,
                             @PathParam("type") final String type,
                             @PathParam("application") final String application)
          throws Exception {

    final List<OpenAPI> oas = openApiService.getOpenApis(null, application, null);
    if (oas == null || oas.isEmpty()) {
      return Response.status(404)
                     .build();
    }
    if (oas.size() > 1) {
      return Response.status(404)
                     .entity("to many apis")
                     .build();
    }
    OpenAPI oa = oas.get(0);
    List<Server> servers = oa.getServers();
    if (servers == null) {
      servers = new ArrayList<>();
    }
    final Server server = new Server();
    final URI baseurl = uriInfo.getBaseUri();
    server.setUrl(baseurl.getScheme() + "://" + baseurl.getAuthority());
    servers.add(server);
    oa.setServers(servers);

    if (type != null && type.trim()
                            .equalsIgnoreCase("yaml")) {
      //
      // final YAMLFactory yf = new YAMLFactory();
      // yf.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
      // yf.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);
      // yf.enable(YAMLGenerator.Feature.SPLIT_LINES);
      // yf.enable(YAMLGenerator.Feature.ALWAYS_QUOTE_NUMBERS_AS_STRINGS);
      //
      // String s = new YAMLMapper(yf).setDefaultPropertyInclusion(Include.NON_NULL)
      // .writer(new DefaultPrettyPrinter())
      // .writeValueAsString(oas);
      //
      // s = s.replaceAll("HTTP", "http");
      // s = s.replaceAll("type: OAUTH2", "type: oauth2");

      return Response.status(Response.Status.OK)
                     .entity(oas)
                     .type("application/yaml")
                     .build();

    } else {
      return Response.status(Response.Status.OK)
                     .entity(oas)
                     .type(MediaType.APPLICATION_JSON_TYPE)
                     .build();
    }

  }
};
