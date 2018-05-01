package org.bipolis.mambo.jaxrs.openapi.basic;

import java.util.ArrayList;
import java.util.List;

import org.bipolis.mambo.jaxrs.openapi.api.MergeException;
import org.bipolis.mambo.jaxrs.openapi.api.MergerService;
import org.bipolis.mambo.jaxrs.openapi.api.OpenApiService;
import org.bipolis.mambo.jaxrs.openapi.api.OpenApiTagType;
import org.bipolis.mambo.jaxrs.openapi.api.fragments.OpenApiFragmentsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Component(service = OpenApiService.class)
public class BaseOpenApiService implements OpenApiService {

	@ObjectClassDefinition
	@interface Config {

		public boolean someProperty() default false;
	};

	@Reference
	MergerService mergerService;
	List<OpenApiFragmentsService> apiAppenderServices = new ArrayList<>();

	// @Reference
	// Logger logger;

	private Config config;

	@Activate
	private void activate(Config config) {
		this.config = config;

	};

	@Reference
	private JaxrsServiceRuntime jaxrsServiceRuntime;

	@Reference(service = OpenApiFragmentsService.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	void bindApiAppenderService(OpenApiFragmentsService apiAppenderService) {

		// logger.debug(l -> l.debug("Bind {}", apiAppenderService));
		apiAppenderServices.add(apiAppenderService);
	}

	@Override
	public OpenAPI getOpenApis(List<String> basePaths, List<OpenApiTagType> filterTagTypes) throws Exception {

		// logger.debug(l -> l.debug("getOpenApi {} {}"));

		final List<OpenAPI> openAPIs = new ArrayList<>();
		;

		if (apiAppenderServices == null) {
			throw new Exception("Apis Api Found");
		}

		for (String basePath : basePaths) {

			final List<OpenAPI> basePathApiList = new ArrayList<>();
			;
			for (OpenApiFragmentsService fragmentsService : apiAppenderServices) {

				basePathApiList.add(fragmentsService.getFragmentOpenApi(basePath));
			}

			OpenAPI pathOpenAPI = mergerService.merge(basePathApiList);

			openAPIs.add(pathOpenAPI);
		}

		return mergeOpenApis(openAPIs, filterTagTypes);
	}

	private OpenAPI mergeOpenApis(List<OpenAPI> openAPIs, List<OpenApiTagType> filterTagTypes) throws MergeException {
		// TODO Auto-generated method stub

		// check count of different apis.
		// (if >1)
		// extra group type is Application

		return mergerService.merge(openAPIs);
	}

	void unbindApiAppenderService(OpenApiFragmentsService apiAppenderService) {

		// logger.debug(l -> l.debug("Unbind {}", apiAppenderService));
		apiAppenderServices.remove(apiAppenderService);
	}

}
