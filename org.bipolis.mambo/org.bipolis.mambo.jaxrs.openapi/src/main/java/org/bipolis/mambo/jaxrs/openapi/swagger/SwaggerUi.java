package org.bipolis.mambo.jaxrs.openapi.swagger;

import static org.bipolis.mambo.jaxrs.openapi.ApiConstants.API_KEY_HEADER;
import static org.bipolis.mambo.jaxrs.openapi.ApiConstants.AUTHORIZATION_HEADER;
import static org.bipolis.mambo.jaxrs.openapi.ApiConstants.CONTENT_TYPE_HEADER;
import static org.bipolis.mambo.jaxrs.openapi.ApiConstants.SWAGGER_UI_PATH_PATTERN;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.service.log.LogService;

//@Component(service = SwaggerUi.class, immediate = true, property = {
//		HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN + "=" + SWAGGER_UI_PATH_PATTERN,
//		HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX + "=/org/bipolis/mambo/jaxrs/openapi/swagger/ui" })
public class SwaggerUi {

	public static final Set<String> CORS_HEADERS = new HashSet<>(
			Arrays.asList(CONTENT_TYPE_HEADER, API_KEY_HEADER, AUTHORIZATION_HEADER));

	@Reference
	private LogService logService;

	@Activate
	private void activate() {
		if (logService != null) {
			logService.log(LogService.LOG_DEBUG, "Activated: " + getClass().getName());
		}

	}
}
