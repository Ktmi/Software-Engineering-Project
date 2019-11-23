package com.bluejay.server.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;

import com.bluejay.server.common.Thread;
import com.bluejay.server.db.DatabaseFacade;

@Path("/")
@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
public class HomePage {

	@Inject
	private DatabaseFacade databaseFacade;

	@GET
	@Template(name = "/index.ftl")
	@ErrorTemplate(name = "/error.ftl")
	@Produces(MediaType.TEXT_HTML)
	public Map<String, Object> presentHomepage() {
		Map<String, Object> a = new HashMap<String, Object>();
		List<Object> items = new ArrayList<Object>();
		a.put("mostViewed", items);
		a.put("mostLiked", items);
		a.put("recents", items);
		return a;
	}

	@Path("/browse")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Thread> getThreads(@QueryParam("p") @DefaultValue("1") int page) throws SQLException {
		return databaseFacade.getThreads(page - 1, 50);
	}

}
