package com.bluejay.server.logic.rest;

import java.security.Key;
import java.sql.SQLException;

import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.ws.rs.DELETE;

//import java.security.Key;

//import javax.crypto.KeyGenerator;
//import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.bluejay.server.common.User;
import com.bluejay.server.db.DatabaseFacade;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/auth")
@Produces("application/json")
public class Auth {

	@Inject
	private KeyGenerator keyGenerator;

	@Inject
	private DatabaseFacade databaseFacade;

	@POST
	public Response verifyLogin(@FormParam("username") String username, @FormParam("password") String secret) {
		// "SELECT userid FROM users WHERE username = ?"
		User user = new User();
		user.setUsername(username);
		user.setPassword(secret);
		try {
			databaseFacade.validateLogin(user);
		} catch (SQLException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		String token = issueToken(user);
		return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
	}

	@DELETE
	public Response logout() {
		return Response.ok().header(HttpHeaders.AUTHORIZATION, null).build();
	}

	private String issueToken(User user) {
		Key key = keyGenerator.generateKey();
		String jwtToken = Jwts.builder().setSubject(user.getUsername()).claim("username", user.getUsername())
				.claim("userid", user.getUserid()).signWith(SignatureAlgorithm.HS512, key).compact();
		return null;
	}

	public User verifyToken(String token) throws Exception {
		Key key = keyGenerator.generateKey();
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		User user = new User();
		user.setUserid((int) claims.get("userid"));
		user.setUsername((String) claims.get("username"));
		return user;

	}

}