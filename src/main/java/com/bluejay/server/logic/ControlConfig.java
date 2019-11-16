package com.bluejay.server.logic;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

/**
 * Configuration for the {@link org.glassfish.jersey.servlet.ServletContainer}.
 * Injected into the servlet container during
 * initialization of the WebApp. 
 */
@ApplicationPath("/")
public class ControlConfig extends ResourceConfig {
    public ControlConfig() {
        //Set package to expose components of
        packages("com.bluejay.server.logic");
        //Adds in Templating for pages
        property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "templates/freemarker");
        register(FreemarkerMvcFeature.class);
    }
}