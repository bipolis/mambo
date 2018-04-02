package org.bipolis.mambo.jaxrs.openapi.basic;

import java.util.ArrayList;
import java.util.List;

import org.bipolis.mambo.jaxrs.openapi.api.OpenApiService;
import org.bipolis.mambo.jaxrs.openapi.api.OpenApiTagType;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import io.swagger.v3.oas.models.OpenAPI;

@Component(service = OpenApiService.class)
public class BaseOpenApiService implements OpenApiService {

  @ObjectClassDefinition
  @interface Config {

    public boolean someProperty() default false;
  };

  List<OpenApiFragmentsService> apiAppenderServices = new ArrayList<>();

  // @Reference
  // Logger logger;

  private Config config;

  @Activate
  private void activate(Config config) {
    this.config = config;

  };

  @Reference
  private JaxrsServiceRuntime jaxrsServiceRuntime;



  @Reference(
          service = OpenApiFragmentsService.class,
          cardinality = ReferenceCardinality.MULTIPLE,
          policy = ReferencePolicy.DYNAMIC)
  void bindApiAppenderService(OpenApiFragmentsService apiAppenderService) {

    // logger.debug(l -> l.debug("Bind {}", apiAppenderService));
    apiAppenderServices.add(apiAppenderService);
  }

  @Override
  public List<OpenAPI> getOpenApis(List<String> apiNames,
                                   String version,
                                   List<OpenApiTagType> groupType) {

    // logger.debug(l -> l.debug("getOpenApi {} {}"));

    final List<OpenAPI> openAPIs = new ArrayList<>();;

    if (apiAppenderServices == null) {
      return new ArrayList<>();
    }

    for (String apiName : apiNames) {


      for (OpenApiFragmentsService fragmentsService : apiAppenderServices) {
        fragmentsService.getFragmentOpenApis(apiName, version);
      }


    }
    return mergeOpenApis(openAPIs, groupType);
  }



  private List<OpenAPI> mergeOpenApis(List<OpenAPI> openAPIs,
                                      List<OpenApiTagType> groupType) {
    // TODO Auto-generated method stub

    // check count of different apis.
    // (if >1)
    // extra group type is Application
    return openAPIs;
  }

  void unbindApiAppenderService(OpenApiFragmentsService apiAppenderService) {

    // logger.debug(l -> l.debug("Unbind {}", apiAppenderService));
    apiAppenderServices.remove(apiAppenderService);
  }



}
