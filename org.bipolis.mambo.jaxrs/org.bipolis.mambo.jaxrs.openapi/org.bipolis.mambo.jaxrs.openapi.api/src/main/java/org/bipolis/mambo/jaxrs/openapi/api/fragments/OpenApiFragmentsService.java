package org.bipolis.mambo.jaxrs.openapi.api.fragments;

import java.util.Comparator;

import io.swagger.v3.oas.models.OpenAPI;

public interface OpenApiFragmentsService extends Comparator<OpenApiFragmentsService> {

	@Override
	public default int compare(OpenApiFragmentsService o1, OpenApiFragmentsService o2) {

		return o1.getPriority() - o2.getPriority();
	}

	OpenAPI getFragmentOpenApi(String apiBase);

	int getPriority();

}
