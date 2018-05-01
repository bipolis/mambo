package org.bipolis.mambo.jaxrs.openapi.producer.bundle.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

	private final BundleContext context = FrameworkUtil.getBundle(ExampleTest.class).getBundleContext();

	@BeforeClass
	public static void beforeAll() {

		System.out.println("TEST PRE");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testExample() throws InterruptedException {

		System.out.println("TEST1");
		ServiceTracker<OpenApiFragmentsService, ? extends OpenApiFragmentsService> tracker = new ServiceTracker<>(
				context, OpenApiFragmentsService.class.getName(), null);

		System.out.println("TEST2");
		tracker.open();

		System.out.println("TEST3");
		OpenApiFragmentsService openApiFragmentsService = tracker.waitForService(5 * 1000);


		System.out.println("TEST4");
		assertNotNull("Service should not be null", openApiFragmentsService);


		System.out.println("TEST5");
		assertEquals("fetch correct Service", openApiFragmentsService.getClass().getName(),
				"org.bipolis.mambo.jaxrs.openapi.producer.bundle.BundleApiAppanderService");

		System.out.println("TEST6");
		OpenAPI openAPI = openApiFragmentsService.getFragmentOpenApi(ExampleApplication.APP_NAME);

		

		System.out.println("TEST7");
		// this test application, default app.
		assertNotNull("expected Applications", openAPI);

		

		System.out.println("TEST8");
		
		Bundle bundle = FrameworkUtil.getBundle(ExampleApplication.class);


		System.out.println("TEST0");
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
		assertEquals(info.getVersion(), bundle.getVersion().toString());

		System.out.println("TESTEND");
		tracker.close();
		System.out.println("TESTEND2");
	}

	private static String getBundleProp(Bundle bundle, String property) {

		String valueFromBundle = null;
		if (bundle != null) {
			valueFromBundle = bundle.getHeaders().get(property);
		}
		return valueFromBundle;
	}
}
