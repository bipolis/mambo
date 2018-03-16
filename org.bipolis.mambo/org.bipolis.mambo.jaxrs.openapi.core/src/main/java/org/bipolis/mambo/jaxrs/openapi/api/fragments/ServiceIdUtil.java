package org.bipolis.mambo.jaxrs.openapi.api.fragments;

import java.util.Arrays;
import java.util.Collection;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;

public class ServiceIdUtil {

  @SuppressWarnings("unchecked")
  public static <S> S getServiceByServiceID(BundleContext bundleContext,
                                            Class<S> clazz,
                                            long serviceId)
          throws InvalidSyntaxException {

    String classname = null;
    if (clazz != null) {
      classname = clazz.getName();
    }
    ServiceReference<?>[] srs =
            bundleContext.getServiceReferences(classname, "(service.id=" + serviceId + ")");
    Collection<ServiceReference<?>> srcoll = Arrays.asList(srs);

    for (ServiceReference<?> serviceReference : srcoll) {
      return (S) bundleContext.getService(serviceReference);

    }
    return null;
  }

  private ApplicationDTO getApplicationDTOByName(JaxrsServiceRuntime jaxrsServiceRuntime,
                                                 String name) {

    RuntimeDTO runtimeDTO = jaxrsServiceRuntime.getRuntimeDTO();

    if (runtimeDTO.applicationDTOs != null) {
      for (ApplicationDTO applicationDTO : runtimeDTO.applicationDTOs) {

        if (name.equals(applicationDTO.name)) {

          return applicationDTO;
        }

      }
    }

    return null;

  }


}
