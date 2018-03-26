package org.bipolis.mambo.jaxrs.openapi.api.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Application;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.ExtensionDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import org.osgi.util.tracker.ServiceTracker;
import io.swagger.v3.oas.models.OpenAPI;

public abstract class AbstractJaxRsApiFragmentService implements OpenApiFragmentsService {


  @Override
  public List<OpenAPI> getFragmentOpenApis(String apiName,
                                           String version) {
    BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass())
                                               .getBundleContext();

    List<OpenAPI> openAPIs = new ArrayList<>();
    List<ApplicationDTO> applicationDTOs =
            Arrays.asList(getJaxrsServiceRuntime().getRuntimeDTO().applicationDTOs);
    applicationDTOs.add(getJaxrsServiceRuntime().getRuntimeDTO().defaultApplication);


    for (ApplicationDTO applicationDTO : applicationDTOs) {

      try {
        Application application = ServiceIdUtil.getServiceByServiceID(bundleContext,
                Application.class, applicationDTO.serviceId);
        OpenAPI applicationOpenAPI = createOpenApiForApplication(applicationDTO, application);

        for (ExtensionDTO extensionDTO : applicationDTO.extensionDTOs) {

          Object extension =
                  ServiceIdUtil.getServiceByServiceID(bundleContext, null, extensionDTO.serviceId);

          applicationOpenAPI = handleOpenApiForExtentionInApplication(applicationOpenAPI,
                  applicationDTO, application, extensionDTO, extension);

        }
        for (ResourceMethodInfoDTO resourceMethodInfoDTOapplication : applicationDTO.resourceMethods) {
          applicationOpenAPI = handleOpenApiForRessourceMethofInforInApplication(applicationOpenAPI,
                  applicationDTO, application, resourceMethodInfoDTOapplication);

        }

        for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {

          applicationOpenAPI =
                  handleOpenApiForRessource(applicationOpenAPI, applicationDTO, resourceDTO);
          for (ResourceMethodInfoDTO resourceMethodInfoDTO : resourceDTO.resourceMethods) {

            Object ressource =
                    ServiceIdUtil.getServiceByServiceID(bundleContext, null, resourceDTO.serviceId);
            applicationOpenAPI = handleOpenApiForRessourceMethofInforInRessource(applicationOpenAPI,
                    applicationDTO, application, resourceDTO, ressource, resourceMethodInfoDTO);

          }


        }
        openAPIs.add(applicationOpenAPI);
      } catch (InvalidSyntaxException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return openAPIs;
  }

  private OpenAPI handleOpenApiForExtentionInApplication(OpenAPI applicationOpenAPI,
                                                         ApplicationDTO applicationDTO,
                                                         Application application,
                                                         ExtensionDTO extensionDTO,
                                                         Object extension) {
    // TODO Auto-generated method stub
    return null;
  }

  protected abstract OpenAPI handleOpenApiForRessource(OpenAPI applicationOpenAPI,
                                                       ApplicationDTO applicationDTO,
                                                       ResourceDTO resourceDTO);

  protected abstract OpenAPI
          handleOpenApiForRessourceMethofInforInRessource(OpenAPI applicationOpenAPI,
                                                          ApplicationDTO applicationDTO,
                                                          Application application,
                                                          ResourceDTO resourceDTO,
                                                          Object ressource,
                                                          ResourceMethodInfoDTO resourceMethodInfoDTO);

  protected abstract OpenAPI
          handleOpenApiForRessourceMethofInforInApplication(OpenAPI applicationOpenAPI,
                                                            ApplicationDTO applicationDTO,
                                                            Application ressourceApplication,
                                                            ResourceMethodInfoDTO resourceMethodInfoDTOapplication);

  protected abstract OpenAPI createOpenApiForApplication(ApplicationDTO applicationDTO,
                                                         Application application);

  private final JaxrsServiceRuntime getJaxrsServiceRuntime() {

    return service(JaxrsServiceRuntime.class);
  }



  protected Map<Class<?>, ServiceTracker<?, ?>> serviceTrackers =
          Collections.synchronizedMap(new IdentityHashMap<>());

  @SuppressWarnings("unchecked")
  protected <S> S service(final Class<S> serviceType) {
    final ServiceTracker<S, ? extends S> tracker;

    if (serviceTrackers.containsKey(serviceType)) {
      tracker = (ServiceTracker<S, ? extends S>) serviceTrackers.get(serviceType);
    } else {
      final BundleContext context = FrameworkUtil.getBundle(getClass())
                                                 .getBundleContext();
      tracker = new ServiceTracker<>(context, serviceType, null);
      tracker.open();
      serviceTrackers.put(serviceType, tracker);
    }

    final S service = tracker.getService();

    if (service == null) {
      throw new RuntimeException("Service Not Found");
    } else {
      return service;
    }
  }
}
