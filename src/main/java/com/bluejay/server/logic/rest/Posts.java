package com.bluejay.server.logic.rest;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/post")
@Produces("application/json")
public class Posts
{
    @POST
    public Response createPost(@HeaderParam(HttpHeaders.AUTHORIZATION) String session,
                               @FormParam("title") String title,
                               @FormParam("text") String text)
    {
        // "INSERT INTO posts(userid,text) VALUES (?,?)"
        //  SELECT @@IDENTITY as 'Identity'
        // INSERT INTO thread(postid,title) VALUES (identity,?)

        return Response.ok().build();
    }

}