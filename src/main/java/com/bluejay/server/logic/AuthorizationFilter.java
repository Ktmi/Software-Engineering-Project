package com.bluejay.server.logic;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Map;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.bluejay.server.common.User;

import io.jsonwebtoken.JwtException;

/**
 * Checks is users have permission for a resource.
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */

@Provider
public class AuthorizationFilter implements ContainerRequestFilter {

	@Inject
	private Authentication authentication;

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		Method method = resourceInfo.getResourceMethod();
		if (method.isAnnotationPresent(PermitAll.class)) {
			return;
		}
		if (method.isAnnotationPresent(DenyAll.class)) {
			requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
			return;
		}
		Map<String, Cookie> cookies = requestContext.getCookies();
		String token = cookies.get("UserToken").getValue();
		try {
			final User user = authentication.verifyToken(token);

			requestContext.setSecurityContext(new SecurityContext() {
				@Override
				public Principal getUserPrincipal() {
					return user;
				}

				// Not used yet???
				@Override
				public boolean isUserInRole(String role) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isSecure() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public String getAuthenticationScheme() {
					// TODO Auto-generated method stub
					return null;
				}
			});

		} catch (JwtException e) {
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
			return;
		}

	}
}