package org.bipolis.mambo.jaxrs.itest.example.basic.application;

import javax.ws.rs.core.Application;

//@Component(service = Application.class)
//
//@JaxrsName(BasicApplicationA.APPLICATION_NAME)
//@JaxrsApplicationBase("/" + BasicApplicationA.APPLICATION_NAME)
public class BasicApplicationA extends Application {

	public static final String APPLICATION_NAME = "BasicApplicationA";

}
