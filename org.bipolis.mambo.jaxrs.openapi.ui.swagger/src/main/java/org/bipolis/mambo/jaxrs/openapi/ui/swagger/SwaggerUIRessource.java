package org.bipolis.mambo.jaxrs.openapi.ui.swagger;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bipolis.mambo.jaxrs.annotation.mediatype.binary.NameBindingBinaryData;
import org.bipolis.mambo.jaxrs.annotation.mediatype.binary.RequiresBinaryDataProvider;
import org.bipolis.mambo.jaxrs.openapi.api.OpenApiApplication;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@JaxrsName(SwaggerUIRessource.RESOURECE_NAME)
@JaxrsApplicationSelect("(" + JaxrsWhiteboardConstants.JAX_RS_NAME + "="
        + OpenApiApplication.APPLICATION_NAME + ")")
@JaxrsResource
@Component(service = SwaggerUIRessource.class)
@Path("ui/swagger")
public class SwaggerUIRessource {
  public final static String RESOURECE_NAME = "SwaggerUiRessource";

  @GET
  @Produces(MediaType.WILDCARD)
  @RequiresBinaryDataProvider
  @NameBindingBinaryData
  @javax.ws.rs.Path("/{subResources:.*}")
  public Response getStaticRessources(@javax.ws.rs.PathParam("subResources") String sPath) {

    if (sPath == null || sPath.isEmpty()) {
      sPath = "index.html";
    }
    System.out.println(sPath);
    try {
      InputStream is = SwaggerUIRessource.class.getResourceAsStream("res/" + sPath);
      BufferedInputStream bis = new BufferedInputStream(is);
      byte[] targetArray = new byte[bis.available()];
      bis.read(targetArray);

      String mediaType = MediaType.WILDCARD;
      if (sPath.endsWith("html")) {
        mediaType = MediaType.TEXT_HTML;
      }
      if (sPath.endsWith("css")) {
        mediaType = "text/css";
      }
      if (sPath.endsWith("ja")) {
        mediaType = "application/javascript";
      }


      if ("swagger-ui.css".equals(sPath)) {

        // config can later replace swaggeruicolor
        String css = new String(targetArray);
        css = css.replaceAll("#89bf04", "#00bf04");// head
        css = css.replaceAll("#547f00", "#007f00");// head


        css = css.replaceAll("#49cc90", "#00bf04");// post border
        css = css.replaceAll("73,204,144,.1", "0,204,144,.1");// post bg

        css = css.replaceAll("#fca130", "#00a130");// put border
        css = css.replaceAll("252,161,48,.1", "252,161,48,.1");// post bg


        css = css.replaceAll("#f93e3e", "#003e3e");// delete border
        css = css.replaceAll("249,62,62,.1", "249,62,62,.1");// delete bg


        css = css.replaceAll("#61affe", "#00affe");// get border
        css = css.replaceAll("97,175,254,.1", "0,175,254,.1");// get bg


        css = css.replaceAll("#50e3c2", "#00e3c2");// patch border
        css = css.replaceAll("80,227,194,.1", "80,227,194,.1");// get bg


        css = css.replaceAll("#9012fe", "#0012fe");// head border
        css = css.replaceAll("144,18,254,.1", "144,18,254,.1");// head bg


        css = css.replaceAll("#0d5aa7", "#005aa7");// options border
        css = css.replaceAll("13,90,167,.1", "13,90,167,.1");// options bg



        targetArray = css.getBytes();
      }
//      is = new ByteArrayInputStream(targetArray);

      return Response.ok(targetArray, mediaType)
                     .build();
    } catch (final Exception ioe) {
      return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                     .entity(ioe.getMessage())
                     .build();

    }



  }
}
