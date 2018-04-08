package org.org.bipolis.mambo.jaxrs.openapi.ui.redoc;

import java.net.URI;

import org.bipolis.mambo.jaxrs.openapi.api.UiProvider;
import org.osgi.service.component.annotations.Component;

@Component(service = UiProvider.class)
public class RedocUIProvider implements UiProvider {

  private static final String SWAGGER_UI_PATH = "openapi/ui/swagger";

  @Override
  public String getName() {

    return "Redoc-UI";
  }


  @Override
  public String getResponseTypes() {

    return "yaml";
  }

  @Override
  public String getUrl(URI baseUrl,
                       String openapiPath) {



    final String uri = "https://rebilly.github.io/ReDoc/?url=" + openapiPath;

    return uri;
  }

}
