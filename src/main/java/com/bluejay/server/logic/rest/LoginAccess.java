package com.bluejay.server.logic.rest;

import java.sql.SQLException;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.bluejay.server.common.User;
import com.bluejay.server.db.DatabaseFacade;
import com.bluejay.server.logic.Authentication;

@Path("/login")
public class LoginAccess {

	@Inject
	DatabaseFacade databaseFacade;

	@Inject
	Authentication authentication;

	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(User user) {
		try {
			databaseFacade.validateLogin(user);
			String token = "Bearer " + authentication.issueToken(user);
			return Response.ok().header(HttpHeaders.AUTHORIZATION, token).build();
		} catch (SQLException e) {

		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@DELETE
	@PermitAll
	public Response logout() {
		return Response.ok().header(HttpHeaders.AUTHORIZATION, null).build();
	}

}
