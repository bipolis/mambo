package org.bipolis.mambo.jaxrs.openapi.api;

import java.util.Arrays;
import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;

public interface OpenApiService {

  default OpenAPI getOpenApis()
          throws Exception {

    return getOpenApis(null, Arrays.asList(OpenApiTagType.DEFAULT));
  }

  OpenAPI getOpenApis(List<String> basePaths,
                      List<OpenApiTagType> filterTagTypes)
          throws Exception;

}
