package org.bipolis.mambo.jaxrs.ext.media.json.itest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class ExampleTest {

	private final BundleContext context = FrameworkUtil.getBundle(ExampleTest.class).getBundleContext();

	@Test
	public void testExample() {

		assertTrue(true);
	}

}
