package org.bipolis.mambo.jaxrs.itest.example.basic.ressource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

//@Component(service = RessourseInDefaultApplicationA.class)
//@JaxrsResource
//@JaxrsApplicationSelect("(" + JaxRSWhiteboardConstants.JAX_RS_NAME + "="
//		+ JaxRSWhiteboardConstants.JAX_RS_DEFAULT_APPLICATION + ")")
//@JaxrsName(RessourseInDefaultApplicationA.RESSOURCE_NAME)
//@Path("/" + RessourseInDefaultApplicationA.RESSOURCE_NAME)
public class RessourseInDefaultApplicationA {

	public static final String RESSOURCE_NAME = "RessourseInDefaultApplicationA";

	@GET
	@Path("/value")
	public String getValue() {
		return getClass().getName();

	}

};
