package org.bipolis.mambo.jaxrs.itest.example.basic.ressource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service = RessourseInDefaultApplicationA.class)
@JaxrsResource
@JaxrsApplicationSelect("(" + JaxrsWhiteboardConstants.JAX_RS_NAME + "="
        + JaxrsWhiteboardConstants.JAX_RS_DEFAULT_APPLICATION + ")")
@JaxrsName(RessourseInDefaultApplicationA.RESSOURCE_NAME)
@Path("/" + RessourseInDefaultApplicationA.RESSOURCE_NAME)
public class RessourseInDefaultApplicationA {

  public static final String RESSOURCE_NAME = "RessourseInDefaultApplicationA";

  @GET
  @Path("/value")
  public String getValue() {
    return getClass().getName();

  }

};
