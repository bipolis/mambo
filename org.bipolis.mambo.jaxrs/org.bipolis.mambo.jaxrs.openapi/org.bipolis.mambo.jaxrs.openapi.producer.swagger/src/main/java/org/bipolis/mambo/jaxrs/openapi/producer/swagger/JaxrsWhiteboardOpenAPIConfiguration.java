package org.bipolis.mambo.jaxrs.openapi.producer.swagger;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.models.OpenAPI;

public class JaxrsWhiteboardOpenAPIConfiguration implements OpenAPIConfiguration {

  @Override
  public Long getCacheTTL() {

    return null;
  }

  @Override
  public String getFilterClass() {
    return null;
  }

  @Override
  public Collection<String> getIgnoredRoutes() {
    return null;
  }

  @Override
  public OpenAPI getOpenAPI() {
    return null;
  }

  @Override
  public String getReaderClass() {
    return null;
  }

  @Override
  public Set<String> getResourceClasses() {
    return null;
  }

  @Override
  public Set<String> getResourcePackages() {

    return null;
  }

  @Override
  public String getScannerClass() {

    return null;
  }

  @Override
  public Map<String, Object> getUserDefinedOptions() {

    return null;
  }

  @Override
  public Boolean isPrettyPrint() {

    return false;
  }

  @Override
  public Boolean isReadAllResources() {

    return true;
  }

}
