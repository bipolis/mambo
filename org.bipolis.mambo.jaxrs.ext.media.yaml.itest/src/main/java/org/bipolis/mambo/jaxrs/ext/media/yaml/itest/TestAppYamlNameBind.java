package org.bipolis.mambo.jaxrs.ext.media.yaml.itest;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = {Application.class})

@JaxrsName(TestAppYamlNameBind.NAME)
@JaxrsApplicationBase("/" + TestAppYamlNameBind.NAME)

public class TestAppYamlNameBind extends Application {
  public static final String NAME = "TestAppYamlNameBind";


}
