package org.bipolis.mambo.jaxrs.openapi.api.fragments;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import io.swagger.v3.oas.models.OpenAPI;

public abstract class AbstractJaxRsApiFragmentService implements OpenApiFragmentsService {


  @Override
  public List<OpenAPI> getFragmentOpenApis(URL server,
                                           String apiName,
                                           String version) {

    List<OpenAPI> openAPIs = new ArrayList<>();
    List<ApplicationDTO> applicationDTOs =
            Arrays.asList(getJaxrsServiceRuntime().getRuntimeDTO().applicationDTOs);
    applicationDTOs.add(getJaxrsServiceRuntime().getRuntimeDTO().defaultApplication);


    for (ApplicationDTO applicationDTO : applicationDTOs) {



      for (ResourceMethodInfoDTO resourceMethodInfoDTOapplication : applicationDTO.resourceMethods) {

      }

      for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {


        for (ResourceMethodInfoDTO resourceMethodInfoDTO : resourceDTO.resourceMethods) {

        }
      }
    }
    return openAPIs;
  }

  protected abstract JaxrsServiceRuntime getJaxrsServiceRuntime();

}
