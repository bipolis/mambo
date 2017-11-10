package org.bipolis.mambo.jaxrs.itest.example.basic.application;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

//@Component(service = RessourceInBasicAppA.class)
//@JaxrsApplicationSelect("=(" + JaxRSWhiteboardConstants.JAX_RS_NAME + "=" + BasicApplicationA.APPLICATION_NAME + ")")
//@JaxrsName(RessourceInBasicAppA.RESSOURCE_NAME)
//@JaxrsResource
//@Path("/"+RessourceInBasicAppA.RESSOURCE_NAME)
public class RessourceInBasicAppA {

	public static final String RESSOURCE_NAME = "RessourceInBasicAppA";

	@GET
	@Path("/value")
	public String getValue() {
		return getClass().getName();

	}
};
