package com.bluejay.server.logic;

import java.io.IOException;
import java.security.Key;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter implements ContainerRequestFilter {

	@Resource(name = "")
	private Logger logger;

	@Resource(name = "")
	private KeyGenerator keyGenerator;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authHeaderVal = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		String token = authHeaderVal.substring("Bearer".length()).trim();
		try {
			Key key = keyGenerator.generateKey();
			Jwts.parser().setSigningKey(key).parseClaimsJwt(token);
			logger.info("#### valid token : " + token);
		} catch (Exception e) {
			logger.severe("#### invalid token : " + token);
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}
}