package org.bipolis.mambo.jaxrs.annotation.security;

import org.osgi.annotation.bundle.Requirement;

@Requirement(namespace = "security", name = "default", version = "1.0.0")
public @interface RequiresSecurityContextProvider {

}
