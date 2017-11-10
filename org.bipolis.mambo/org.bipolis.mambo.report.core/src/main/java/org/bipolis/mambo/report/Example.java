package org.bipolis.mambo.report;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(service = Object.class, immediate = true)
public class Example {

	@Activate
	private void runfirst() {
		System.out.println("activate");

	}

}