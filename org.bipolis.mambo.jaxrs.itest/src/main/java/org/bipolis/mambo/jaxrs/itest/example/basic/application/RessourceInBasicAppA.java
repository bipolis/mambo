package org.bipolis.mambo.jaxrs.itest.example.basic.application;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service = RessourceInBasicAppA.class)
@JaxrsApplicationSelect("(" + JaxrsWhiteboardConstants.JAX_RS_NAME + "="
        + BasicApplicationA.APPLICATION_NAME + ")")
@JaxrsName(RessourceInBasicAppA.RESSOURCE_NAME)
@JaxrsResource
@Path("/" + RessourceInBasicAppA.RESSOURCE_NAME)
public class RessourceInBasicAppA {

  public static final String RESSOURCE_NAME = "RessourceInBasicAppA";

  @GET
  @Path("/value")
  public String getValue() {
    return getClass().getName();

  }
};
