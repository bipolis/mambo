package org.bipolis.mambo.jaxrs.openapi.producer.bundle;

import org.bipolis.mambo.jaxrs.openapi.api.OpenApiOverrideConfig;
import org.osgi.framework.Bundle;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


public class BundleApiAppanderService {
  private static String doBundlePropOverride(boolean isOverride,
                                             String value,
                                             Bundle bundle,
                                             String property) {

    String valueFromBundle = null;
    if (bundle != null) {
      valueFromBundle = bundle.getHeaders()
                              .get(property);

    }
    if (bundle != null && (isOverride || value == null || value.trim()
                                                               .isEmpty())
            && valueFromBundle != null && !valueFromBundle.trim()
                                                          .isEmpty()) {
      value = valueFromBundle;
    }
    return value;
  }

  public static OpenAPI enrichInfoWithOsgiBundleHeaders(OpenAPI openAPI,
                                                        OpenApiOverrideConfig cfg,
                                                        Bundle bundle) {


    if (bundle != null) {

      Info info = openAPI.getInfo();
      if (info == null) {
        info = new Info();
      }
      info.setTitle(
              doBundlePropOverride(cfg.overrideInfo(), info.getTitle(), bundle, "Bundle-Name"));
      info.setDescription(doBundlePropOverride(cfg.overrideInfo(), info.getDescription(), bundle,
              "Bundle-Description"));
      info.setTermsOfService(doBundlePropOverride(cfg.overrideInfo(), info.getTermsOfService(),
              bundle, "Bundle-Copyright"));
      info.setVersion(doBundlePropOverride(cfg.overrideInfo(), info.getVersion(), bundle,
              "Bundle-Version"));

      License license = info.getLicense();
      if (license == null) {
        license = new License();
      }
      license.setName(doBundlePropOverride(cfg.overrideLicense(), license.getName(), bundle, ""));
      license.setUrl(doBundlePropOverride(cfg.overrideLicense(), license.getUrl(), bundle,
              "Bundle-License"));
      info.setLicense(license);

      Contact contact = info.getContact();
      if (contact == null) {
        contact = new Contact();
      }
      contact.setName(doBundlePropOverride(cfg.overrideContact(), contact.getName(), bundle,
              "Bundle-Vendor"));
      contact.setUrl(doBundlePropOverride(cfg.overrideContact(), contact.getUrl(), bundle, ""));
      contact.setEmail(doBundlePropOverride(cfg.overrideContact(), contact.getEmail(), bundle,
              "Bundle-ContactAddress"));
      info.setContact(contact);
      openAPI.setInfo(info);

      ExternalDocumentation externalDocs = openAPI.getExternalDocs();
      if (externalDocs == null) {
        externalDocs = new ExternalDocumentation();

        externalDocs.setUrl(
                doBundlePropOverride(cfg.overrideExternalDocs(), null, bundle, "Bundle-DocURL"));
      }

      openAPI.setExternalDocs(externalDocs);
    }
    return openAPI;
  }
}
