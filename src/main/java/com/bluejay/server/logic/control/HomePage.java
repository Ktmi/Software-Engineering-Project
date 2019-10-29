package com.bluejay.server.logic.control;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.ErrorTemplate;

@Path("/")
public class HomePage {

    @GET
    @Produces({"text/html"})
    @Template(name = "/index.ftl")
    @ErrorTemplate(name = "/error.ftl")
    public Map<String,Object> presentHomepage()
    {
        Map<String,Object> a = new HashMap<String,Object>();
        List<Object> items = new ArrayList<Object>();
        a.put("mostViewed",items);
        a.put("mostLiked",items);
        a.put("recents",items);
        return a;
    }
}