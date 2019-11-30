package com.bluejay.server.logic.rest;

import java.sql.SQLException;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.bluejay.server.common.User;
import com.bluejay.server.db.DatabaseFacade;
import com.bluejay.server.logic.Authentication;

@Path("/login")
public class LoginAccess {

	@Inject
	private DatabaseFacade databaseFacade;

	@Inject
	private Authentication authentication;

	@Context
	private UriInfo uriContext;

	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@BeanParam User user) throws Exception {
		try {
			databaseFacade.validateLogin(user);
			String token = "Bearer " + authentication.issueToken(user);
			return Response.temporaryRedirect(uriContext.getBaseUri()).header(HttpHeaders.AUTHORIZATION, token).build();
		} catch (SQLException e) {

		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@DELETE
	@PermitAll
	public Response logout() {
		return Response.ok().header(HttpHeaders.AUTHORIZATION, null).build();
	}

}
