package org.org.bipolis.mambo.jaxrs.openapi.producer.swagger;

import org.bipolis.mambo.jaxrs.openapi.api.fragments.AbstractJaxRsApiFragmentService;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.log.Logger;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = OpenApiFragmentsService.class)
public class JaxrsApiAppenderService extends AbstractJaxRsApiFragmentService {

  @ObjectClassDefinition
  @interface Config {

    public int priority()

    default 0;


  }



  private Config config;


  @Activate
  private void activate(Config config) {
    this.config = config;
  }

  @Reference
  Logger logger;
  @Reference
  JaxrsServiceRuntime jaxrsServiceRuntime;



  @Override
  protected JaxrsServiceRuntime getJaxrsServiceRuntime() {
    return jaxrsServiceRuntime;
  }



  @Override
  public int getPriority() {

    return config.priority();
  }

}
