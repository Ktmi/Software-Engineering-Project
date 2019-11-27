package com.bluejay.server.logic;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.bluejay.server.common.Post;
import com.bluejay.server.common.Reply;
import com.bluejay.server.common.Thread;
import com.bluejay.server.common.User;
import com.bluejay.server.db.DatabaseFacade;

@Path("/post")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostAccess {

	@Inject
	private DatabaseFacade databaseFacade;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Post getPost(Post post) {
		try {
			databaseFacade.getPost(post);
		} catch (SQLException e) {
			post.setContent("Content unavailable.");
		}
		return post;
	}

	@Path("/thread")
	@POST
	public Thread createThread(Thread thread) {

		return null;
	}

	@Path("/reply")
	@POST
	public Reply createReply(Reply reply, @Context SecurityContext securityContext) {
		User user = (User) securityContext.getUserPrincipal();
		reply.setUserid(user.getUserid());
		return null;
	}

	@Path("/browse")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Thread> getThreads(@QueryParam("p") @DefaultValue("1") int page) throws SQLException {
		return databaseFacade.getThreads(page - 1, 50);
	}
}
