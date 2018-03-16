package org.bipolis.mambo.jaxrs.annotation.mediatype.json;

import org.osgi.annotation.bundle.Capability;

@Capability(namespace = "media.type", name = "Json", version = "1.0.0")
@JsonNameBinding
public @interface JsonProvider {

}
