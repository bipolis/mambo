package org.bipolis.mambo.jaxrs.openapi.ui.swagger.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.bipolis.mambo.jaxrs.openapi.api.OpenApiApplication;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;
import org.osgi.util.tracker.ServiceTracker;

public class ExampleTest {

  private final BundleContext context = FrameworkUtil.getBundle(ExampleTest.class)
                                                     .getBundleContext();

  @BeforeClass
  public static void beforeAll() {
    try {
      Thread.sleep(1000 * 5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testExample()
          throws IOException,
          InterruptedException {
    ServiceTracker<JaxrsServiceRuntime, ? extends JaxrsServiceRuntime> tracker =
            new ServiceTracker<>(context, JaxrsServiceRuntime.class.getName(), null);

    tracker.open();
    JaxrsServiceRuntime jaxrsServiceRuntime = tracker.waitForService(5 * 1000);

    RuntimeDTO runtimeDTO = jaxrsServiceRuntime.getRuntimeDTO();
    assertNotNull(runtimeDTO);
    assertNotNull(runtimeDTO.defaultApplication);
    assertEquals(1, runtimeDTO.applicationDTOs.length);
    assertEquals(1, runtimeDTO.applicationDTOs.length);

    assertEquals(OpenApiApplication.APPLICATION_NAME, runtimeDTO.applicationDTOs[0].name);

    // URL url = new URL("http://localhost:8080/openapi/ui/swagger/index.html");
    // HttpURLConnection con = (HttpURLConnection) url.openConnection();
    // con.setRequestMethod("GET");
    // assertEquals(200, con.getResponseCode());

  }

}
