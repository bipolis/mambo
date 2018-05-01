package org.bipolis.mambo.jaxrs.openapi.ui.swagger;

import java.net.URI;

import org.bipolis.mambo.jaxrs.openapi.api.UiProvider;
import org.osgi.service.component.annotations.Component;

@Component(service = UiProvider.class)
public class SwaggerUIProvider implements UiProvider {

	private static final String SWAGGER_UI_PATH = "openapi/ui/swagger";

	@Override
	public String getName() {

		return "Swagger-UI";
	}

	@Override
	public String getResponseTypes() {

		return "yaml";
	}

	@Override
	public String getUrl(URI baseUrl, String openapiPath) {

		final String swaggeruri = baseUrl.getScheme() + "://" + baseUrl.getAuthority() + "/" + SWAGGER_UI_PATH;
		String url = swaggeruri + "/index.html?url=" + openapiPath;
		return url;
	}

}
