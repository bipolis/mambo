package org.org.bipolis.mambo.jaxrs.openapi.producer.bundle.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;
import io.swagger.v3.oas.models.OpenAPI;

public class ExampleTest {

  private final BundleContext context = FrameworkUtil.getBundle(ExampleTest.class)
                                                     .getBundleContext();

  @Test
  public void testExample() {

    ServiceTracker<OpenApiFragmentsService,OpenApiFragmentsService>  tracker = new ServiceTracker<>(context, OpenApiFragmentsService.class, null);
    tracker.open();
    OpenApiFragmentsService openApiFragmentsService=tracker.getService();


    assertNotNull("Service not null", openApiFragmentsService);

    assertEquals("fetch correct Service", openApiFragmentsService.getClass()
                                                                 .getName(),
            "org.bipolis.mambo.jaxrs.openapi.producer.bundle.BundleApiAppanderService");



    List<OpenAPI> openApis = openApiFragmentsService.getFragmentOpenApis(null, null);


    assertEquals("expected Applications", openApis.size(), 2);

    for (OpenAPI openAPI : openApis) {
      System.out.println(openAPI);


      // Bundle-Name: Bundle-Name
      // Bundle-Description: Bundle-Description
      // Bundle-Category: Bundle-Category
      // Bundle-Copyright: Bundle-Copyright
      // Bundle-License: Bundle-License
      // Bundle-Vendor: Bundle-Vendor
      // Bundle-ContactAddress: Bundle-ContactAddress
      // Bundle-DocURL: Bundle-DocURL
    }
  }

}
