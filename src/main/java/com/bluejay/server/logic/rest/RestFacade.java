package com.bluejay.server.logic.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/rest")
public class RestFacade extends ResourceConfig {
    public RestFacade() {
        packages("com.bluejay.server.logic.rest");
        
    }
}