package org.org.bipolis.mambo.jaxrs.ext.media.xml.itest;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = {Application.class})

@JaxrsName(TestAppXmlNameBind.NAME)
@JaxrsApplicationBase("/" + TestAppXmlNameBind.NAME)

public class TestAppXmlNameBind extends Application {
  public static final String NAME = "TestAppXmlNameBind";


}
