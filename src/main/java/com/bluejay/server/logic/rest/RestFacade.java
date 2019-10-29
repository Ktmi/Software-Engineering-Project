package com.bluejay.server.logic.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class RestFacade extends ResourceConfig {
    public RestFacade() {
        packages("com.bluejay.server.logic.rest");
        
    }
}