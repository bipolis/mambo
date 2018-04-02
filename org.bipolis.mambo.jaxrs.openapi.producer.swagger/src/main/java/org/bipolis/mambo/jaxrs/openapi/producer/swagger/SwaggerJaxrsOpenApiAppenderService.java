package org.bipolis.mambo.jaxrs.openapi.producer.swagger;

import javax.ws.rs.core.Application;

import org.bipolis.mambo.jaxrs.openapi.api.MergeException;
import org.bipolis.mambo.jaxrs.openapi.api.MergerService;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.AbstractJaxRsApiFragmentService;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.ExtensionDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import io.swagger.v3.oas.models.OpenAPI;

@Component(service = OpenApiFragmentsService.class)
public class SwaggerJaxrsOpenApiAppenderService extends AbstractJaxRsApiFragmentService {

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
  MergerService mergerService;

  // @Reference
  // Logger logger;



  @Override
  public int getPriority() {

    return config.priority();
  }



  @Override
  protected OpenAPI handleOpenApiForRessource(OpenAPI baseOpenAPI,
                                              ApplicationDTO applicationDTO,
                                              Application application,
                                              ResourceDTO resourceDTO,
                                              Object ressource) {
    JaxrsWhiteboardOpenApiReader reader = new JaxrsWhiteboardOpenApiReader(applicationDTO);
    OpenAPI ressourceOpenApi = reader.read(ressource.getClass());

    try {
      baseOpenAPI = mergerService.merge(baseOpenAPI, ressourceOpenApi);
    } catch (MergeException e) {

      e.printStackTrace();
    }
    return baseOpenAPI;
  }



  @Override
  protected OpenAPI handleOpenApiForRessourceMethofInforInRessource(OpenAPI baseOpenAPI,
                                                                    ApplicationDTO applicationDTO,
                                                                    Application application,
                                                                    ResourceDTO resourceDTO,
                                                                    Object ressource,
                                                                    ResourceMethodInfoDTO resourceMethodInfoDTO) {
    return baseOpenAPI;
  }



  @Override
  protected OpenAPI handleOpenApiForRessourceMethofInforInApplication(OpenAPI baseOpenAPI,
                                                                      ApplicationDTO applicationDTO,
                                                                      Application ressourceApplication,
                                                                      ResourceMethodInfoDTO resourceMethodInfoDTOapplication) {
    return baseOpenAPI;
  }



  @Override
  protected OpenAPI createOpenApiForApplication(ApplicationDTO applicationDTO,
                                                Application application) {
    JaxrsWhiteboardOpenApiReader reader = new JaxrsWhiteboardOpenApiReader(applicationDTO);
    OpenAPI openAPI = reader.read(application.getClass());
    System.out.println(openAPI);
    return openAPI;
  }

  @Override
  protected OpenAPI handleOpenApiForExtentionInApplication(OpenAPI baseOpenAPI,
                                                           ApplicationDTO applicationDTO,
                                                           Application application,
                                                           ExtensionDTO extensionDTO,
                                                           Object extension) {


    JaxrsWhiteboardOpenApiReader reader = new JaxrsWhiteboardOpenApiReader(applicationDTO);
    OpenAPI extensionsOpenApi = reader.read(extension.getClass());

    try {
      baseOpenAPI = mergerService.merge(baseOpenAPI, extensionsOpenApi);
    } catch (MergeException e) {

      e.printStackTrace();
    }

    return baseOpenAPI;
  }


}
