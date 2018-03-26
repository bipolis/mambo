package org.bipolis.mambo.jaxrs.openapi.producer.swagger;

import javax.ws.rs.core.Application;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.AbstractJaxRsApiFragmentService;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import org.osgi.service.log.Logger;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import io.swagger.v3.oas.models.OpenAPI;

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



  @Override
  public int getPriority() {

    return config.priority();
  }



  @Override
  protected OpenAPI handleOpenApiForRessource(OpenAPI applicationOpenAPI,
                                              ApplicationDTO applicationDTO,
                                              ResourceDTO resourceDTO) {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  protected OpenAPI handleOpenApiForRessourceMethofInforInRessource(OpenAPI applicationOpenAPI,
                                                                    ApplicationDTO applicationDTO,
                                                                    Application application,
                                                                    ResourceDTO resourceDTO,
                                                                    Object ressource,
                                                                    ResourceMethodInfoDTO resourceMethodInfoDTO) {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  protected OpenAPI handleOpenApiForRessourceMethofInforInApplication(OpenAPI applicationOpenAPI,
                                                                      ApplicationDTO applicationDTO,
                                                                      Application ressourceApplication,
                                                                      ResourceMethodInfoDTO resourceMethodInfoDTOapplication) {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  protected OpenAPI createOpenApiForApplication(ApplicationDTO applicationDTO,
                                                Application application) {
    // TODO Auto-generated method stub
    return new OpenAPI();
  }

}
