package org.bipolis.mambo.jaxrs.openapi.api;

import java.net.URI;

public interface UiProvider {

	public String getName();

	public String getUrl(URI baseUrl, String openapiPath);

	public String getResponseTypes();

}
