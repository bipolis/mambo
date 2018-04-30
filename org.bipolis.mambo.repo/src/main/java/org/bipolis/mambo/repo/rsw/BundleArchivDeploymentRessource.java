package org.bipolis.mambo.repo.rsw;

import static org.bipolis.mambo.repo.rsw.RepoApplication.APP_NAME;
import static org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_NAME;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bipolis.mambo.jaxrs.annotation.mediatype.binary.RequiresBinaryDataProvider;
import org.bipolis.mambo.jaxrs.annotation.mediatype.json.RequiresJsonProvider;
import org.bipolis.mambo.repo.BundleRepository;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd = org.bipolis.mambo.repo.rsw.BundleArchivDeploymentRessource.Config.class)
@Component(service = BundleArchivDeploymentRessource.class)

@JaxrsApplicationSelect("(" + JAX_RS_NAME + "=" + APP_NAME + ")")
@RequiresBinaryDataProvider
@RequiresJsonProvider
@JaxrsResource
@JaxrsName(BundleArchivDeploymentRessource.NAME)
@javax.ws.rs.Path("/bar")
public class BundleArchivDeploymentRessource {

  @ObjectClassDefinition(
          name = "Bundle Repository",
          description = "Configuration of the BundleArchivDeploymentRessource")
  public @interface Config {

  }

  private static final Logger logger =
          LoggerFactory.getLogger(BundleArchivDeploymentRessource.class);
  public static final String NAME = "BundleArchivDeploymentRessource";

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
    // TODO: on the fly generateing Bundlearchive

    return Response.status(404)
                   .build();

  }

  Map<String, BundleRepository> bundleRepositorys = new HashMap<>();

  @Reference
  public void bindRepo(final BundleRepository repo) {
    logger.debug("repository service bound: " + repo);
    bundleRepositorys.put(repo.getName(), repo);
  }

  public void unbindRepo(BundleRepository repo) {
    logger.debug("repository service unbound: " + repo);
    bundleRepositorys.remove(repo.getName());
  }

}
