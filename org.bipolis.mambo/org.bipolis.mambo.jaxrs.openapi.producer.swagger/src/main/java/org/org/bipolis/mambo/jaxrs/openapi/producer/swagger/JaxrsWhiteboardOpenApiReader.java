package org.org.bipolis.mambo.jaxrs.openapi.producer.swagger;

import javax.ws.rs.core.Application;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import io.swagger.v3.jaxrs2.Reader;

public class JaxrsWhiteboardOpenApiReader extends Reader {

  private ApplicationDTO applicationDTO;

  private JaxrsWhiteboardOpenApiReader() {

  }

  public JaxrsWhiteboardOpenApiReader(ApplicationDTO applicationDTO, Application application) {
    this();
    this.applicationDTO = applicationDTO;

    setApplication(application);
  }

  @Override
  protected String resolveApplicationPath() {

    return applicationDTO.base + "/" + super.resolveApplicationPath();
  }

}
