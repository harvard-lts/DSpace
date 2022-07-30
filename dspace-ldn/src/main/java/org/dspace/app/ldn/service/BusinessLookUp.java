/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.ldn.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides the lookup implementation for the Business Delegate Pattern
 * 
 * @author Stefano Maffei (steph-ieffam @ 4Science)
 *
 */
public class BusinessLookUp {

    private Map<String, BusinessService> services = new HashMap<>();

    /**
     * Initializes the services Map with any found BusinessService
     * 
     * @param businessServices
     */
    @Autowired
    public BusinessLookUp(List<BusinessService> businessServices) {
        businessServices.forEach(service -> services.put(service.getServiceName().toUpperCase(), service));
    }

    /**
     * Returns the proper service used by the Business Delegate
     * 
     * @param  serviceName the name of the service
     * @return             BusinessService the proper service
     */
    public BusinessService getBusinessService(String serviceName) {
        return services.get(serviceName.toUpperCase());
    }
}
