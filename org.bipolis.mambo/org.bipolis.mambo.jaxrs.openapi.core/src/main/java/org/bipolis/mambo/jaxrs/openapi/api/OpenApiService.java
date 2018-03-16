package org.bipolis.mambo.jaxrs.openapi.api;

import java.net.URL;
import java.util.List;
import io.swagger.v3.oas.models.OpenAPI;

public interface OpenApiService {

  default List<OpenAPI> getOpenApis() {

    return getOpenApis(null, null, null);
  }


  List<OpenAPI> getOpenApis(URL server,
                            String apiName,
                            String version);

}
