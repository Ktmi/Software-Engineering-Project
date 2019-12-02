package com.bluejay.server.logic.rest;

import java.net.URI;
import java.sql.SQLException;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bluejay.server.common.User;
import com.bluejay.server.db.DatabaseFacade;

/**
 * Provides access to info about users, and creating accounts.
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
@Path("/user")
public class UserAccess {

	@Inject
	private DatabaseFacade databaseFacade;

	public void setDatabaseFacade(DatabaseFacade databaseFacade) {
		this.databaseFacade = databaseFacade;
	}

	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User whoIs(User user) throws SQLException {
		databaseFacade.getUser(user);
		return user;
	}

	@Path("/new")
	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser(@BeanParam User user) throws SQLException {
		databaseFacade.addUser(user);
		return Response.temporaryRedirect(URI.create("/bluejay-server/createAcc/createAccountSucceeded.html")).build();
	}

}
