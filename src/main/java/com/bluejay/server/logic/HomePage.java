package com.bluejay.server.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;

import com.bluejay.server.db.DatabaseFacade;

@Path("/rest")
@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
public class HomePage {

	@Resource(name = "jbdc/bluejay-db")
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

	/*
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public List<Post>
	 * search(@QueryParam("query") String query, @QueryParam("orderBy") List<String>
	 * orderBy,
	 * 
	 * @QueryParam("from") int from, @QueryParam("to") int to) throws SQLException {
	 * return databaseFacade.search(query, orderBy, from, to); }
	 */
}
