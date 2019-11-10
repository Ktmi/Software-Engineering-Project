package com.bluejay.server.logic;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;


public class AuthorizationFilter
implements ContainerRequestFilter
{

    @Override
    public void filter(ContainerRequestContext requestContext)
    throws IOException
    {
        String authHeaderVal = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    }
}