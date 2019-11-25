package com.bluejay.server.logic;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.bluejay.server.common.Post;
import com.bluejay.server.common.Thread;
import com.bluejay.server.db.DatabaseFacade;

@Path("/")
public class HomePage {

	@Inject
	private DatabaseFacade databaseFacade;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Post getPost(Post post) throws SQLException {
		databaseFacade.getPost(post);
		return post;
	}

	@Path("/browse")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Thread> getThreads(@QueryParam("p") @DefaultValue("1") int page) throws SQLException {
		return databaseFacade.getThreads(page - 1, 50);
	}
}
