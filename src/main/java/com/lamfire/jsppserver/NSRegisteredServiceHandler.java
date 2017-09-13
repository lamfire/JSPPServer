package com.lamfire.jsppserver;

import com.lamfire.hydra.MessageFactory;
import com.lamfire.hydra.Session;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.SERVICE;
import com.lamfire.logger.Logger;
import com.lamfire.utils.StringUtils;

/**
 * NSRegisteredServiceHandler
 */
public class NSRegisteredServiceHandler implements ServiceHandler{
    private static final Logger LOGGER = Logger.getLogger(NSRegisteredServiceHandler.class);
    private final NSRegistry registry = new NSRegistry();

    public void nsPackage(String packageName){
        registry.registerPackage(packageName);
    }

    public void onService(Session session, SERVICE service) {
        String ns = service.getNs();
        if(StringUtils.isBlank(ns)){
            LOGGER.error("Service packet failed,'ns' element not found - " + service);
            return;
        }
        NSService nsService = registry.lookup(ns);
        if(nsService == null){
            LOGGER.error("Not registered NS ["+ns+"] found - " + service);
            return;
        }
        SERVICE result = nsService.onNSService(service);
        if(result != null) {
            session.send(MessageFactory.message(0, JSPPUtils.encode(result)));
        }
    }
}
