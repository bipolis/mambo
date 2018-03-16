package org.bipolis.mambo.jaxrs.annotation.security;

import org.osgi.annotation.bundle.Capability;

@Capability(namespace = "security", name = "default", version = "1.0.0")
@SecurityNameBinding
public @interface SecurityContextProvider {

}
