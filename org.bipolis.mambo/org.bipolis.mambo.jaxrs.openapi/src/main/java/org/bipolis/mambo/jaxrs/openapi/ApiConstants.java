package org.bipolis.mambo.jaxrs.openapi;

import javax.ws.rs.core.MediaType;

public interface ApiConstants {

	// header constants
	/** A header name constant for the Allow-Origin CORS header. */
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
	/** A header name constant for the Allow-Methods CORS header. */
	public static final String ACCESS_CONTROL_ALLOW_METHODS_HEADER = "Access-Control-Allow-Methods";
	/** A header name constant for the Allow-Headers CORS header. */
	public static final String ACCESS_CONTROL_ALLOW_HEADERS_HEADER = "Access-Control-Allow-Headers";
	/** A header name constant for the <code>api_key</code> header. */
	public static final String API_KEY_HEADER = "api_key";
	/** A header name constant for the authorization header. */
	public static final String AUTHORIZATION_HEADER = "Authorization";
	/** A header name constant for the content disposition header. */
	public static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
	/** A header name constant for the content type header. */
	public static final String CONTENT_TYPE_HEADER = "Content-Type";

	/**
	 * The allowed methods for the {@link #ACCESS_CONTROL_ALLOW_METHODS_HEADER}
	 */
	public static final String ALLOW_METHODS = "GET, POST, PUT, DELETE";

	/** A media type constant for JSON with UTF-8 as its defined charset. */
	public static final String JSON_UTF8 = MediaType.APPLICATION_JSON + ";charset=UTF-8";
	/** A media type constant for HTML with UTF-8 as its defined charset. */
	public static final String HTML_UTF8 = MediaType.TEXT_HTML + ";charset=UTF-8";

	// API path constants
	public static final String PATH_SEPARATOR = "/";
	public static final String CATCH_ALL_PATTERN = PATH_SEPARATOR + "*";
	public static final String API_DOC_PATH_SEGMENT = "api-doc";
	public static final String API_DOC_PATH = PATH_SEPARATOR + API_DOC_PATH_SEGMENT;
	public static final String ERRORS_PATH_SEGEMENT = "errors";
	public static final String ERRORS_PATH = PATH_SEPARATOR + ERRORS_PATH_SEGEMENT;
	public static final String SWAGGER_UI_PATH_SEGEMENT = "swagger-ui";
	public static final String SWAGGER_UI_PATH = PATH_SEPARATOR + API_DOC_PATH_SEGMENT + PATH_SEPARATOR
			+ SWAGGER_UI_PATH_SEGEMENT;
	public static final String SWAGGER_UI_PATH_PATTERN = SWAGGER_UI_PATH + CATCH_ALL_PATTERN;

	// API path parameters
	public static final String PATH_PARAM_START_DELIM = "{";
	public static final String PATH_PARAM_END_DELIM = "}";
	public static final String ID_PATH_PARAM = "id";
	public static final String ID_PATH_PARAM_TEMPLATE = PATH_PARAM_START_DELIM + ID_PATH_PARAM + PATH_PARAM_END_DELIM;
	public static final String ERROR_CODE_PATH_PARAM = "errorCode";
	public static final String ERROR_CODE_PATH_PARAM_TEMPLATE = PATH_PARAM_START_DELIM + ERROR_CODE_PATH_PARAM
			+ PATH_PARAM_END_DELIM;
	public static final String VERSION_PATH_PARAM = "version";
	public static final String VERSION_PATH_PARAM_TEMPLATE = PATH_PARAM_START_DELIM + VERSION_PATH_PARAM
			+ PATH_PARAM_END_DELIM;

}
