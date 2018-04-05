package org.bipolis.mambo.repo.rsw;

import static org.bipolis.mambo.repo.rsw.RepoApplication.APP_NAME;
import static org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_NAME;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bipolis.mambo.jaxrs.annotation.mediatype.binary.RequiresBinaryDataProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.json.RequiresJsonProvider;
import org.bipolis.mambo.repo.BundleRepository;
import org.bipolis.mambo.repo.BundleRepositoryException;
import org.bipolis.mambo.repo.RepositoryAdditionResult;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.indexer.impl.MimeType;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Designate(ocd = org.bipolis.mambo.repo.rsw.ObrRepositoryRessource.Config.class)
@Component(service = ObrRepositoryRessource.class)

@JaxrsApplicationSelect("(" + JAX_RS_NAME + "=" + APP_NAME + ")")
@RequiresBinaryDataProvider
@RequiresJsonProvider
@JaxrsResource
@JaxrsName("ObrRepositoryRessource")
@javax.ws.rs.Path("/obr")
public class ObrRepositoryRessource {

  @ObjectClassDefinition(
          name = "Bundle Repository",
          description = "Configuration of the Bundle Repository")
  public @interface Config {


  }

  private static final Logger logger = LoggerFactory.getLogger(ObrRepositoryRessource.class);
  public static final String NAME = "RepositoryRessource";

  private BundleRepository bundleRepository;
  private Config config;

  @Activate
  protected void activate(final Config config) {
    this.config = config;
  }

  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @javax.ws.rs.Path("/{subResources:.*}")
  public Response getFile(@javax.ws.rs.PathParam("subResources") final String path) {

    logger.debug("GET " + path);

    if (path.toLowerCase()
            .endsWith(BundleRepository.EXT_JAR)
            || path.toLowerCase()
                   .endsWith(BundleRepository.EXT_EAS)) {
      // direct access to Bundle
      final Path bundleFile = bundleRepository.bundleFile(path);

      if (bundleFile == null) {
        return Response.status(Response.Status.NOT_FOUND)
                       .build();

      }

      // response.setContentType(MimeType.Bundle.toString());
      // response.setContentLengthLong(Files.size(bundleFile));

      try (OutputStream out = new ByteArrayOutputStream()) {
        Files.copy(bundleFile, out);
        out.flush();
        return Response.ok(out, MimeType.Bundle.toString())
                       .build();
      } catch (final BundleRepositoryException bre) {
        return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                       .entity(bre.getMessage())
                       .build();
      } catch (final IOException ioe) {
        return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                       .entity(ioe.getMessage())
                       .build();

      }

    }

    // other paths as access to Index-Files
    try {
      final Path indexFile = bundleRepository.indexFile(path);

      if (indexFile == null) {
        return Response.status(Status.NOT_FOUND)
                       .build();

      }

      // response.setContentType(MediaType.APPLICATION_XML);
      // response.setContentLengthLong(Files.size(indexFile));

      try (OutputStream out = new ByteArrayOutputStream()) {
        Files.copy(indexFile, out);
        out.flush();
        return Response.ok(out, MediaType.APPLICATION_OCTET_STREAM)
                       .build();
      } catch (final IOException ioe) {
        return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                       .entity(ioe.getMessage())
                       .build();
      }
    } catch (final BundleRepositoryException bre) {
      return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                     .entity(bre.getMessage())
                     .build();

    }

  }

  @Consumes(MediaType.APPLICATION_OCTET_STREAM)
  @POST
  @javax.ws.rs.Path("/{subResources:.*}")
  public Response addFile(@javax.ws.rs.PathParam("subResources") final String path,
                          final InputStream inputStream) {
    logger.debug("POST " + path);

    try {

      final RepositoryAdditionResult result = bundleRepository.addBundle(inputStream, path);

      return Response.created(new URI(result.location()))
                     .build();

    } catch (final BundleRepositoryException bre) {
      logger.debug(bre.getMessage(), bre);
      return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                     .entity(bre.getMessage())
                     .build();
    } catch (final URISyntaxException e) {
      logger.debug(e.getMessage(), e);
      return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                     .entity(e.getMessage())
                     .build();
    }
  }


  @DELETE
  @javax.ws.rs.Path("/{subResources:.*}")
  public Response removeFile(@javax.ws.rs.PathParam("subResources") final String path) {

    logger.debug("DELETE " + path);

    try {
      final boolean deleted = bundleRepository.deleteBundle(path);
      return Response.status(deleted ? Status.OK : Status.NOT_FOUND)
                     .build();
    } catch (final BundleRepositoryException bre) {
      return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                     .entity(bre.getMessage())
                     .build();
    }
  }



  @Reference
  public void bindRepo(final BundleRepository repo) {
    logger.debug("repository service bound: " + repo);
    bundleRepository = repo;
  }

  public void unbindRepo(BundleRepository repo) {
    logger.debug("repository service unbound: " + repo);
    repo = null;
  }

}
