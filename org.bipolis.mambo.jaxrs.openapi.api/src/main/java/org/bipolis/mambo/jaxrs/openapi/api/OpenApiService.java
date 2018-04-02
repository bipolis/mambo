package org.bipolis.mambo.jaxrs.openapi.api;

import java.util.Arrays;
import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;

public interface OpenApiService {

  default List<OpenAPI> getOpenApis() {

    return getOpenApis(null, null, Arrays.asList(OpenApiTagType.DEFAULT));
  }


  List<OpenAPI> getOpenApis(List<String> applications,
                            String version,
                            List<OpenApiTagType> tagTypes);

}
