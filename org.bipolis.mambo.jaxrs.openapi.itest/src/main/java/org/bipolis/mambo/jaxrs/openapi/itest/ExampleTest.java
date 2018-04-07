package org.bipolis.mambo.jaxrs.openapi.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.bipolis.mambo.jaxrs.openapi.example.ExampleApplication;
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
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testExample()
          throws InterruptedException {

    ServiceTracker<JaxrsServiceRuntime, ? extends JaxrsServiceRuntime> tracker =
            new ServiceTracker<>(context, JaxrsServiceRuntime.class.getName(), null);

    tracker.open();
    JaxrsServiceRuntime jaxrsServiceRuntime = tracker.waitForService(5 * 1000);

    RuntimeDTO runtimeDTO = jaxrsServiceRuntime.getRuntimeDTO();
    assertNotNull(runtimeDTO);
    assertNotNull(runtimeDTO.defaultApplication);
    assertEquals(1, runtimeDTO.applicationDTOs.length);
    assertEquals(1, runtimeDTO.applicationDTOs.length);

    assertEquals(ExampleApplication.APP_NAME, runtimeDTO.applicationDTOs[0].name);


  }

}
