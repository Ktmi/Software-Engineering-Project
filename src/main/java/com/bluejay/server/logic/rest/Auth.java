package com.bluejay.server.logic.rest;

import java.security.Key;

import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/auth")
@Produces("application/json")
public class Auth
{

    @Inject
    private KeyGenerator keyGenerator;

    @POST
    public Response verifyLogin (@FormParam("username") String username,
                                 @FormParam("password") String secret)
    {
        // "SELECT userid FROM users WHERE username = ?"

        String token = issueToken(username);
        
        return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
    }

    private String issueToken(String username)
    {
        Key key = keyGenerator.generateKey();
        return null;
    }



}