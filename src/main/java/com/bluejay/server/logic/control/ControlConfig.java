package com.bluejay.server.logic.control;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

@ApplicationPath("/")
public class ControlConfig extends ResourceConfig {
    public ControlConfig() {
        packages("com.bluejay.server.logic.control");
        property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "templates/freemarker");
        register(FreemarkerMvcFeature.class);
    }
}