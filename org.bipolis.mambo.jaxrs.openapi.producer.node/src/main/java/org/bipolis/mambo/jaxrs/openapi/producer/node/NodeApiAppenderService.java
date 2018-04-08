package org.bipolis.mambo.jaxrs.openapi.producer.node;

import java.util.List;

import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import io.swagger.v3.oas.models.OpenAPI;

@Component(service = OpenApiFragmentsService.class)
public class NodeApiAppenderService implements OpenApiFragmentsService {

  @ObjectClassDefinition
  @interface Config {

    public int priotity() default 0;
  }

  @Activate
  private void activate(Config config) {
    this.config = config;

  }

  private Config config;
  @Reference
  private Logger logger;

  @Override
  public int getPriority() {

    return config.priotity();
  }

  @Override
  public List<OpenAPI> getFragmentOpenApis(String apiName,
                                           String version) {
    // TODO Auto-generated method stub
    return null;
  }

}
