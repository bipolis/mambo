package org.bipolis.mambo.jaxrs.openapi.api;

import java.net.URL;
import java.time.Instant;

import io.swagger.v3.oas.models.OpenAPI;

public class OpenApiProducerResult {
	URL node;
	String application;
	String ressource;
	OpenAPI openAPI;
	Instant creationTime;

}
