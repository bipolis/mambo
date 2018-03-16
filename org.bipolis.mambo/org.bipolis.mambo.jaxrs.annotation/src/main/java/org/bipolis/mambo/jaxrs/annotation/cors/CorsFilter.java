package org.bipolis.mambo.jaxrs.annotation.cors;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtension;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = {ContainerResponseFilter.class})
@JaxrsName("CorsFilter")
@JaxrsExtension
@CorsFilterProvider
public class CorsFilter implements ContainerResponseFilter {

  // header constants
  /** A header name constant for the Allow-Origin CORS header. */
  private static final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
  /** A header name constant for the Allow-Methods CORS header. */
  private static final String ACCESS_CONTROL_ALLOW_METHODS_HEADER = "Access-Control-Allow-Methods";
  /** A header name constant for the Allow-Headers CORS header. */
  private static final String ACCESS_CONTROL_ALLOW_HEADERS_HEADER = "Access-Control-Allow-Headers";
  /** A header name constant for the <code>api_key</code> header. */
  private static final String API_KEY_HEADER = "api_key";

  /** A header name constant for the authorization header. */
  private static final String AUTHORIZATION_HEADER = "Authorization";

  /** A header name constant for the content type header. */
  private static final String CONTENT_TYPE_HEADER = "Content-Type";

  private static final Set<String> CORS_HEADERS =
          new HashSet<>(Arrays.asList(CONTENT_TYPE_HEADER, API_KEY_HEADER, AUTHORIZATION_HEADER));

  /**
   * The allowed methods for the {@link #ACCESS_CONTROL_ALLOW_METHODS_HEADER}
   */
  private static final String ALLOW_METHODS = "GET, POST, PUT, DELETE";

  @Override
  public void filter(final ContainerRequestContext request,
                     final ContainerResponseContext response)
          throws IOException {

    final Set<String> requiredHeaders = new HashSet<>();
    requiredHeaders.addAll(CORS_HEADERS);

    final StringBuilder headers = new StringBuilder();
    final Iterator<String> headerIter = requiredHeaders.iterator();

    while (headerIter.hasNext()) {
      headers.append(headerIter.next())
             .append(headerIter.hasNext() ? ", " : "");
    }

    response.getHeaders()
            .add(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
    response.getHeaders()
            .add(ACCESS_CONTROL_ALLOW_METHODS_HEADER, ALLOW_METHODS);
    response.getHeaders()
            .add(ACCESS_CONTROL_ALLOW_HEADERS_HEADER, headers.toString());
  }
}
