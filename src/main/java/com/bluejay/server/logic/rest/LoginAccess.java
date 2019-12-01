package com.bluejay.server.logic.rest;

import java.sql.SQLException;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.bluejay.server.common.User;
import com.bluejay.server.db.DatabaseFacade;
import com.bluejay.server.logic.Authentication;

/**
 * Provides access to login and logout
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
@Path("/login")
public class LoginAccess {

	@Inject
	private DatabaseFacade databaseFacade;

	@Inject
	private Authentication authentication;

	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@BeanParam User user) {
		try {
			databaseFacade.validateLogin(user);
			String token = authentication.issueToken(user);
			NewCookie cookie = new NewCookie("UserToken", token, "/", "", "authentication", 1000000, false);
			return Response.ok().cookie(cookie).build();
		} catch (SQLException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@DELETE
	@PermitAll
	public Response logout() {
		return Response.ok().header(HttpHeaders.AUTHORIZATION, null).build();
	}

}
