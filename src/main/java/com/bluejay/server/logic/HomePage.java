package com.bluejay.server.logic;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


import org.glassfish.jersey.server.mvc.Template;

import com.bluejay.server.db.DatabaseFacade;

import org.glassfish.jersey.server.mvc.ErrorTemplate;

@Path("/")
@Produces({"text/html"})
public class HomePage {

	@Resource(name = "jbdc/bluejay-db")
	private DatabaseFacade databaseFacade;
	
	
    @GET
    @Template(name = "/index.ftl")
    @ErrorTemplate(name = "/error.ftl")
    @Produces("text/html")
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
