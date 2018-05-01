package org.bipolis.mambo.jaxrs.annotation.cors;

import org.osgi.annotation.bundle.Requirement;

@Requirement(namespace = "filter", name = "cors", version = "1.0.0")
@CorsNameBinding
public @interface RequiresCorsFilter {

}
