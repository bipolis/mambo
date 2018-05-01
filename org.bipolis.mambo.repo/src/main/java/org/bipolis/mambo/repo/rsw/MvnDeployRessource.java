package org.bipolis.mambo.repo.rsw;

import static org.bipolis.mambo.repo.rsw.RepoApplication.APP_NAME;
import static org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_NAME;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd = org.bipolis.mambo.repo.rsw.MvnDeployRessource.Config.class)
@Component(service = MvnDeployRessource.class)

@JaxrsApplicationSelect("(" + JAX_RS_NAME + "=" + APP_NAME + ")")
@RequiresBinaryDataProvider
@RequiresJsonProvider
@JaxrsResource
@JaxrsName("RepositoryRessource")
@javax.ws.rs.Path("/mvn")
public class MvnDeployRessource {

	@ObjectClassDefinition(name = "Bundle Repository", description = "Configuration of the Bundle Repository")
	public @interface Config {

	}

	private static final Logger logger = LoggerFactory.getLogger(MvnDeployRessource.class);
	public static final String NAME = "MvnDeployRessource";

	private BundleRepository bundleRepository;
	private Config config;

	@Activate
	protected void activate(final Config config) {
		this.config = config;
	}

	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@PUT
	@javax.ws.rs.Path("/{subResources:.*}")
	public Response addFile(@javax.ws.rs.PathParam("subResources") final String path, final InputStream inputStream) {
		logger.debug("PUT " + path);

		try {

			if (path.endsWith(".jar") || path.endsWith(".eas")) {
				final RepositoryAdditionResult result = bundleRepository.addBundle(inputStream, path);
				System.out.println(result);

			}
			return Response.status(Status.OK).build();

		} catch (final BundleRepositoryException bre) {
			logger.debug(bre.getMessage(), bre);
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(bre.getMessage()).build();
		}
	}

	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@GET
	@javax.ws.rs.Path("/{subResources:.*}")
	public Response addFile(@javax.ws.rs.PathParam("subResources") final String path) {
		logger.debug("GET " + path);

		try {

			return Response.status(Status.OK).build();

		} catch (final BundleRepositoryException bre) {
			logger.debug(bre.getMessage(), bre);
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(bre.getMessage()).build();
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
