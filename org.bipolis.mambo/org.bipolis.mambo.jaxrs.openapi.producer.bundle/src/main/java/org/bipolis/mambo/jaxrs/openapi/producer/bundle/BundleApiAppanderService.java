package org.bipolis.mambo.jaxrs.openapi.producer.bundle;

import javax.ws.rs.core.Application;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.AbstractJaxRsApiFragmentService;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Component(service = {OpenApiFragmentsService.class, BundleApiAppanderService.class})
public class BundleApiAppanderService extends AbstractJaxRsApiFragmentService {
  private static String getBundleProp(Bundle bundle,
                                      String property) {

    String valueFromBundle = null;
    if (bundle != null) {
      valueFromBundle = bundle.getHeaders()
                              .get(property);
    }
    return valueFromBundle;
  }


  public static OpenAPI fillOSGiBundleHeaders(OpenAPI openAPI,
                                              Bundle bundle) {


    if (bundle != null) {

      Info info = openAPI.getInfo();
      if (info == null) {
        info = new Info();
      }
      info.setTitle(getBundleProp(bundle, "Bundle-Name"));
      info.setDescription(getBundleProp(bundle, "Bundle-Description"));
      info.setTermsOfService(getBundleProp(bundle, "Bundle-Copyright"));
      info.setVersion(getBundleProp(bundle, "Bundle-Version"));

      License license = info.getLicense();
      if (license == null) {
        license = new License();
      }
      license.setName(getBundleProp(bundle, ""));
      license.setUrl(getBundleProp(bundle, "Bundle-License"));
      info.setLicense(license);

      Contact contact = info.getContact();
      if (contact == null) {
        contact = new Contact();
      }
      contact.setName(getBundleProp(bundle, "Bundle-Vendor"));
      contact.setUrl(getBundleProp(bundle, ""));
      contact.setEmail(getBundleProp(bundle, "Bundle-ContactAddress"));
      info.setContact(contact);
      openAPI.setInfo(info);

      ExternalDocumentation externalDocs = openAPI.getExternalDocs();
      if (externalDocs == null) {
        externalDocs = new ExternalDocumentation();

        externalDocs.setUrl(getBundleProp(bundle, "Bundle-DocURL"));
      }

      openAPI.setExternalDocs(externalDocs);
    }
    return openAPI;
  }

  @Override
  public int getPriority() {

    return 1;
  }

  @Override
  protected OpenAPI handleOpenApiForRessource(OpenAPI applicationOpenAPI,
                                              ApplicationDTO applicationDTO,
                                              ResourceDTO resourceDTO) {

    return applicationOpenAPI;
  }


  @Override
  protected OpenAPI handleOpenApiForRessourceMethofInforInApplication(OpenAPI applicationOpenAPI,
                                                                      ApplicationDTO applicationDTO,
                                                                      Application ressourceApplication,
                                                                      ResourceMethodInfoDTO resourceMethodInfoDTOapplication) {

    return applicationOpenAPI;
  }

  @Override
  protected OpenAPI createOpenApiForApplication(ApplicationDTO applicationDTO,
                                                Application application) {
    OpenAPI openAPI = new OpenAPI();

    Bundle bundle = FrameworkUtil.getBundle(application.getClass());
    openAPI = fillOSGiBundleHeaders(openAPI, bundle);
    return openAPI;
  }


  @Override
  protected OpenAPI handleOpenApiForRessourceMethofInforInRessource(OpenAPI applicationOpenAPI,
                                                                    ApplicationDTO applicationDTO,
                                                                    Application application,
                                                                    ResourceDTO resourceDTO,
                                                                    Object ressource,
                                                                    ResourceMethodInfoDTO resourceMethodInfoDTO) {

    return applicationOpenAPI;
  }


}
