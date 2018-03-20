package org.bipolis.mambo.jaxrs.itest.example.basic.ressource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.log.Logger;

@Component(
        service = RessourseInDefaultApplication.class,
        property = {
            JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name="
                    + JaxrsWhiteboardConstants.JAX_RS_DEFAULT_APPLICATION + ")",
            JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", JaxrsWhiteboardConstants.JAX_RS_NAME
                    + "=" + RessourseInDefaultApplication.RESSOURCE_NAME})

@Path("/" + RessourseInDefaultApplication.RESSOURCE_NAME)
public class RessourseInDefaultApplication {

  public static final String RESSOURCE_NAME = "RessourseInDefaultApplication";

  
  @Activate
  public void activate() {

    System.out.println("ssssss");
    logger.audit("ss");
  }

  @Reference
  Logger logger;

  @GET
  @Path("/value")
  public String getValue() {
    logger.audit("aa");
    return getClass().getName();

  }

};
