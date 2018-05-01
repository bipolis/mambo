package org.bipolis.mambo.repo.rsw;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = Application.class)

@JaxrsName(RepoApplication.APP_NAME)
@JaxrsApplicationBase(RepoApplication.BASE)
public class RepoApplication extends Application {

	public static final String BASE = "/repo";
	public static final String APP_NAME = "OSGiRepositoryApplication";

}
