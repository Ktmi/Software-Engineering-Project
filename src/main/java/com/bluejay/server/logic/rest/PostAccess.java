package com.bluejay.server.logic.rest;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.bluejay.server.common.Post;
import com.bluejay.server.common.Reply;
import com.bluejay.server.common.Thread;
import com.bluejay.server.common.User;
import com.bluejay.server.db.DatabaseFacade;

/**
 * Provides access to posts.
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
@Path("/post")
public class PostAccess {

	@Inject
	private DatabaseFacade databaseFacade;

	public void setDatabaseFacade(DatabaseFacade databaseFacade) {
		this.databaseFacade = databaseFacade;
	}

	@POST
	@PermitAll
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

	@Path("/list")
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<Post> getPosts(@QueryParam("p") @DefaultValue("1") int page,
			@QueryParam("thread") @DefaultValue("0") int threadid) throws SQLException {
		if (threadid == 0) {
			return databaseFacade.getThreads(page - 1, 50);
		}
		Thread thread = new Thread();
		thread.setPostid(threadid);
		return databaseFacade.getReplies(thread, page - 1, 50);

	}

	@Path("/thread")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createThread(@BeanParam Thread thread, @Context SecurityContext securityContext)
			throws SQLException {
		User user = (User) securityContext.getUserPrincipal();
		thread.setUserid(user.getUserid());
		databaseFacade.createThread(thread);
		return Response.temporaryRedirect(URI.create("/rest/post/view/" + thread.getPostid())).build();
	}

	@Path("/reply")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Reply createReply(Reply reply, @Context SecurityContext securityContext) throws SQLException {
		User user = (User) securityContext.getUserPrincipal();
		reply.setUserid(user.getUserid());
		databaseFacade.createReply(reply);
		return reply;
	}

	@Path("/view/{threadid}")
	@GET
	@PermitAll
	public Thread viewPage(@PathParam("threadid") int threadid) {
		Thread thread = new Thread();
		thread.setPostid(threadid);
		return thread;
	}

}
