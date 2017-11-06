package org.bipolis.mambo.hello;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component
public class Example {

	@Activate
	void activate() {
		System.out.println("Hello World");
	}
}
