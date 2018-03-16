package org.bipolis.mambo.jaxrs.annotation.mediatype.yaml;

import org.osgi.annotation.bundle.Requirement;

@Requirement(namespace = "media.type", name = "Yaml", version = "1.0.0")
@YamlNameBinding
public @interface RequiresYamlProvider {

}
