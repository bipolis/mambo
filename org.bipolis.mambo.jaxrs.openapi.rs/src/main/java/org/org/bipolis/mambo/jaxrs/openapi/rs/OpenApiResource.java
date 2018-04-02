package org.org.bipolis.mambo.jaxrs.openapi.rs;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.bipolis.mambo.jaxrs.annotation.mediatype.json.NameBindingJsonProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.json.RequiresJsonProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.xml.NameBindingXmlProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.xml.RequiresXmlProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.yaml.NameBindingYamlProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.yaml.RequiresYamlProvider;
import org.bipolis.mambo.jaxrs.openapi.api.OpenApiService;
import org.bipolis.mambo.jaxrs.openapi.api.OpenApiTagType;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Component(service = OpenApiResource.class)
@JaxrsName("OpenApiResource")
@JaxrsResource
@JaxrsApplicationSelect("(" + JaxrsWhiteboardConstants.JAX_RS_NAME + "="
        + OpenApiApplication.APPLICATION_NAME + ")")
@Path(OpenApiResource.BASEPATH)
// @PermitAll
public class OpenApiResource {
  protected static final String BASEPATH = "/doc";

  private static final String SWAGGER_UI_PATH = "/static/swaggerui";

  @Context
  private UriInfo uri;
  // @Reference
  // private Logger logger;
  @Reference
  private OpenApiService openApiService;
  @Reference
  private JaxrsServiceRuntime jaxrsServiceRuntime;


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/application")
  @RequiresYamlProvider
  @RequiresJsonProvider
  @RequiresXmlProvider
  @NameBindingYamlProvider
  @NameBindingJsonProvider
  @NameBindingXmlProvider
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
  @Produces({MediaType.APPLICATION_JSON, "application/yaml", MediaType.APPLICATION_XML})
  @Path("/application/{application}/{type:json|yaml|xml}")
  @RequiresYamlProvider
  @RequiresJsonProvider
  @RequiresXmlProvider
  @NameBindingYamlProvider
  @NameBindingJsonProvider
  @NameBindingXmlProvider
  public Response getOpenApi(@Context final HttpHeaders headers,
                             @Context final UriInfo uriInfo,
                             @PathParam("type") final String type,
                             @PathParam("application") final String application,
                             @QueryParam("tagfilter") List<OpenApiTagType> tagFilters)
          throws Exception {

    if (tagFilters == null || tagFilters.isEmpty()) {
      tagFilters = Arrays.asList(OpenApiTagType.DEFAULT);
    }

    final OpenAPI openAPI = getOpenApi(uriInfo, Arrays.asList(application), tagFilters);

    if (openAPI == null) {
      return Response.status(404)
                     .build();
    }

    String responseType;
    if ("yaml".equalsIgnoreCase(type)) {
      responseType = "application/yaml";
    }
    if ("xml".equalsIgnoreCase(type)) {
      responseType = MediaType.APPLICATION_XML;
    } else {
      responseType = MediaType.APPLICATION_JSON;
    }

    return Response.status(Response.Status.OK)
                   .entity(openAPI)
                   .type(responseType)
                   .build();
  }



  @GET
  @Produces({MediaType.APPLICATION_JSON, "application/yaml", MediaType.APPLICATION_XML})
  @Path("/group/")
  @RequiresYamlProvider
  @RequiresJsonProvider
  @RequiresXmlProvider
  @NameBindingYamlProvider
  @NameBindingJsonProvider
  @NameBindingXmlProvider
  public Response getOpenApi(@Context final HttpHeaders headers,
                             @Context final UriInfo uriInfo,
                             @QueryParam("groupType") final List<OpenApiTagType> groupTypes,
                             @QueryParam("mediaType") final OpenApiResponseType mediaType,
                             @QueryParam("application") final List<String> applications)
          throws Exception {

    final OpenAPI openAPI = getOpenApi(uriInfo, applications, groupTypes);

    if (openAPI == null) {
      return Response.status(404)
                     .build();
    }

    String responseType;

    switch (mediaType) {
      case XML:
        responseType = MediaType.APPLICATION_XML;
        break;
      case YAML:
        responseType = "application/yaml";
        break;
      default:
        responseType = MediaType.APPLICATION_JSON;
        break;
    }

    return Response.status(Response.Status.OK)
                   .entity(openAPI)
                   .type(responseType)
                   .build();
  }

  private OpenAPI getOpenApi(final UriInfo uriInfo,
                             final List<String> applications,
                             List<OpenApiTagType> groupTypes) {
    final List<OpenAPI> oas = openApiService.getOpenApis(applications, null, groupTypes);

    if (oas == null || oas.isEmpty()) {
      return null;

    }
    OpenAPI openAPI = oas.get(0);
    List<Server> servers = openAPI.getServers();
    if (servers == null) {
      servers = new ArrayList<>();
    }
    final Server server = new Server();
    final URI baseurl = uriInfo.getBaseUri();
    server.setUrl(baseurl.getScheme() + "://" + baseurl.getAuthority());
    servers.add(server);
    openAPI.setServers(servers);
    return openAPI;
  }

  @GET
  @Produces("text/html")
  public String getApplicationsListHtml(@Context final UriInfo uriInfo) {

    final URI baseurl = uriInfo.getBaseUri();

    final String swaggeruri =
            baseurl.getScheme() + "://" + baseurl.getAuthority() + SWAGGER_UI_PATH;

    final StringBuilder htmlBuilder = new StringBuilder();

    htmlBuilder.append("<html>");

    for (final String app : getApplication()) {

      htmlBuilder.append("<a href=" + swaggeruri + "/index.html?url=" + uri.getBaseUri()
              + OpenApiResource.BASEPATH.replace("/", "") + "/application/" + app + "/yaml>" + app
              + "</a>");
      htmlBuilder.append("<br>");
    }

    htmlBuilder.append("</html>");
    return htmlBuilder.toString();
  }
};
