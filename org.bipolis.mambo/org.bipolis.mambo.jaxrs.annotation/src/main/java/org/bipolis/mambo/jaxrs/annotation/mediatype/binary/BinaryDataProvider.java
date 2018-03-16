package org.bipolis.mambo.jaxrs.annotation.mediatype.binary;

import org.osgi.annotation.bundle.Capability;

@Capability(namespace = "media.type", name = "BinaryData", version = "1.0.0")
@BinaryDataNameBinding
public @interface BinaryDataProvider {

}
