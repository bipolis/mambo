package org.bipolis.mambo.jaxrs.openapi.producer.swagger;

import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import io.swagger.v3.jaxrs2.Reader;

public class JaxrsWhiteboardOpenApiReader extends Reader {

  private ApplicationDTO applicationDTO;

  private JaxrsWhiteboardOpenApiReader() {
    setConfiguration(new JaxrsWhiteboardOpenAPIConfiguration());
  }

  public JaxrsWhiteboardOpenApiReader(ApplicationDTO applicationDTO) {
    this();
    this.applicationDTO = applicationDTO;

  }

  @Override
  protected String resolveApplicationPath() {

    return applicationDTO.base + "/" + super.resolveApplicationPath();
  }

}
