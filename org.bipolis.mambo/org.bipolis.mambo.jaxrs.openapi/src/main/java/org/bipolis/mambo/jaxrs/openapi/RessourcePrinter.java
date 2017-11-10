package org.bipolis.mambo.jaxrs.openapi;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.jaxrs.runtime.JaxRSServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;

@Component(immediate = true)
public class RessourcePrinter {
	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	JaxRSServiceRuntime jaxRSServiceRuntime;

	@Activate
	public void activate() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {

				}
				RuntimeDTO runtimeDTO = jaxRSServiceRuntime.getRuntimeDTO();
				System.out.println(runtimeDTO);
			}
		}).start();

	}
};
