package com.camel.learning.camelproject.learn;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileRouteControl {

    @Autowired
    CamelContext camelContext;

    boolean state = false;

    public void startTheRoute(String routeId) throws Exception {
        if (!state) {
            camelContext.getRouteController().startRoute(routeId);
            state = true;
        }
        camelContext.getRouteController().resumeRoute(routeId);
        Thread.sleep(2000);
        camelContext.getRouteController().suspendRoute(routeId);
    }
}
