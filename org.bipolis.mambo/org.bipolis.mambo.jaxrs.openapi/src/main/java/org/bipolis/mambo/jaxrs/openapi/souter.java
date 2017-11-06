package org.bipolis.mambo.jaxrs.openapi;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.jaxrs.runtime.JaxRSServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;

@Component
public class souter {
	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	JaxRSServiceRuntime jaxRSServiceRuntime;

	@Activate
	public void activate() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RuntimeDTO runtimeDTO = jaxRSServiceRuntime.getRuntimeDTO();
				System.out.println(runtimeDTO);
			}
		}).start();

	}
};
