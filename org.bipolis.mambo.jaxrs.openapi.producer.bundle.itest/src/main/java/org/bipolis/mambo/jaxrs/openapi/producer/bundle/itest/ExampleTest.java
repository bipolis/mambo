package org.bipolis.mambo.jaxrs.openapi.producer.bundle.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
import org.bipolis.mambo.jaxrs.openapi.example.ExampleApplication;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

public class ExampleTest {

  private final BundleContext context = FrameworkUtil.getBundle(ExampleTest.class)
                                                     .getBundleContext();

  @BeforeClass
  public static void beforeAll() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testExample()
          throws InterruptedException {

    ServiceTracker<OpenApiFragmentsService, ? extends OpenApiFragmentsService> tracker =
            new ServiceTracker<>(context, OpenApiFragmentsService.class.getName(), null);

    tracker.open();
    OpenApiFragmentsService openApiFragmentsService = tracker.waitForService(5 * 1000);

    assertNotNull("Service not null", openApiFragmentsService);

    assertEquals("fetch correct Service", openApiFragmentsService.getClass()
                                                                 .getName(),
            "org.bipolis.mambo.jaxrs.openapi.producer.bundle.BundleApiAppanderService");

    List<OpenAPI> openApis =
            openApiFragmentsService.getFragmentOpenApis(ExampleApplication.APP_NAME, null);

    // this test application, default app.
    assertEquals("expected Applications", openApis.size(), 1);

    Bundle bundle = FrameworkUtil.getBundle(ExampleApplication.class);

    OpenAPI openAPI = openApis.get(0);
    Info info = openAPI.getInfo();

    Contact contact = info.getContact();
    License license = info.getLicense();
    ExternalDocumentation externalDocumentation = openAPI.getExternalDocs();

    assertEquals(info.getTitle(), getBundleProp(bundle, "Bundle-Name"));
    assertEquals(info.getDescription(), getBundleProp(bundle, "Bundle-Description"));
    assertEquals(info.getTermsOfService(), getBundleProp(bundle, "Bundle-Copyright"));
    assertEquals(license.getUrl(), getBundleProp(bundle, "Bundle-License"));
    assertEquals(contact.getName(), getBundleProp(bundle, "Bundle-Vendor"));
    assertEquals(contact.getEmail(), getBundleProp(bundle, "Bundle-ContactAddress"));
    assertEquals(externalDocumentation.getUrl(), getBundleProp(bundle, "Bundle-DocURL"));
    assertEquals(info.getVersion(), bundle.getVersion()
                                          .toString());

    tracker.close();
  }

  private static String getBundleProp(Bundle bundle,
                                      String property) {

    String valueFromBundle = null;
    if (bundle != null) {
      valueFromBundle = bundle.getHeaders()
                              .get(property);
    }
    return valueFromBundle;
  }
}
