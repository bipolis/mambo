package org.bipolis.mambo.jaxrs.restconsole.itest;

import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class ExampleTest {

  private final BundleContext context = FrameworkUtil.getBundle(ExampleTest.class)
                                                     .getBundleContext();

  @Test
  public void testExample() {
    // TODO
  }

}
