package org.org.bipolis.mambo.jaxrs.ext.media.json.itest;

import javax.ws.rs.core.Application;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = {Application.class})

@JaxrsName("a")
@JaxrsApplicationBase("/a")

public class TestAppNameBind extends Application {



}
