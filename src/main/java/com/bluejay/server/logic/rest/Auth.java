package com.bluejay.server.logic.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/rest/auth")
public class Auth
{
    @POST
    public Response verifyLogin (@FormParam("username") String username, @FormParam("password") String secret)
    {
        
        return Response.ok().cookie(new NewCookie("userid",)).build();
    }
}