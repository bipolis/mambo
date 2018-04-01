package org.org.bipolis.mambo.jaxrs.openapi.producer.swagger.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
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

public class SwaggerTest {

  private final BundleContext context = FrameworkUtil.getBundle(SwaggerTest.class)
                                                     .getBundleContext();

  @BeforeClass
  public static void beforeAll() {
    try {
      Thread.sleep(1000);
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
            "org.bipolis.mambo.jaxrs.openapi.producer.swagger.SwaggerJaxrsOpenApiAppenderService");

    List<OpenAPI> openApis = openApiFragmentsService.getFragmentOpenApis(null, null);

    // this test application, default app.
    assertEquals("expected Applications", openApis.size(), 2);

    Bundle bundle = context.getBundle();

    boolean relevantApiFound = false;
    for (OpenAPI openAPI : openApis) {

      Info info = openAPI.getInfo();
      if (openAPI != null && info != null && info.getTitle()
                                                 .equals("Annotation-title")) {
        relevantApiFound = true;

        Contact contact = info.getContact();
        License license = info.getLicense();
        ExternalDocumentation externalDocumentation = openAPI.getExternalDocs();

        assertEquals(info.getTitle(), "Annotation-title");
        assertEquals(info.getVersion(), "Annotation-version");
        assertEquals(info.getTermsOfService(), "Annotation-termsOfService");
        assertEquals(info.getDescription(), "Annotation-description");
        assertEquals(license.getUrl(), "Annotation-License-url");
        assertEquals(license.getName(), "Annotation-License-name");

        assertEquals(contact.getName(), "Annotation-Contact-name");
        assertEquals(contact.getEmail(), "Annotation-Contact-email");
        assertEquals(contact.getUrl(), "Annotation-Contact-url");

        assertEquals(externalDocumentation.getUrl(), "Annotation-ExternalDocumentation-url");
        assertEquals(externalDocumentation.getDescription(),
                "Annotation-ExternalDocumentation-description");


        System.out.println(openAPI);
        assertNotNull(openAPI.getComponents());
        assertNotNull(openAPI.getComponents()
                             .getSecuritySchemes());
        assertNotNull(openAPI.getComponents()
                             .getSecuritySchemes()
                             .get("BasicAuth"));
      }

    }
    assertTrue("the relevant api was tested", relevantApiFound);
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
