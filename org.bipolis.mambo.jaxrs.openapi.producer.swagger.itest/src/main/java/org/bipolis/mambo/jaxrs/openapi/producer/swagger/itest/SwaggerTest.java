package org.bipolis.mambo.jaxrs.openapi.producer.swagger.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
import org.bipolis.mambo.jaxrs.openapi.example.ExampleApplication;
import org.bipolis.mambo.jaxrs.openapi.example.ExampleResource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
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

    List<OpenAPI> openApis =
            openApiFragmentsService.getFragmentOpenApis(ExampleApplication.APP_NAME, null);//

    // this test application, default app.
    assertEquals("expected Applications", 1, openApis.size());

    OpenAPI openAPI = openApis.get(0);

    Info info = openAPI.getInfo();

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

    // Test Extension
    assertNotNull(openAPI.getComponents());
    assertNotNull(openAPI.getComponents()
                         .getSecuritySchemes());
    assertNotNull(openAPI.getComponents()
                         .getSecuritySchemes()
                         .get("BasicAuth"));

    // Test Ressources
    assertNotNull(openAPI.getPaths());
    assertEquals(3, openAPI.getPaths()
                           .size());

    PathItem pathItemApplicationEcho = openAPI.getPaths()
                                              .get("/" + ExampleApplication.APP_NAME + "/echo");
    assertNotNull(pathItemApplicationEcho);

    PathItem pathItemRessourceUpper = openAPI.getPaths()
                                             .get("/" + ExampleApplication.APP_NAME + "/"
                                                     + ExampleResource.NAME + "/upper");
    assertNotNull(pathItemRessourceUpper);

    PathItem pathItemRessourceLowerAndFilter = openAPI.getPaths()
                                                      .get("/" + ExampleApplication.APP_NAME + "/"
                                                              + ExampleResource.NAME
                                                              + "/lowerAndFilter");
    assertNotNull(pathItemRessourceLowerAndFilter);

  }

}
