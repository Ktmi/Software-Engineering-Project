package com.bluejay.server.logic.rest;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/rest/post")
public class Posts
{



    @POST
    public Response createPost(@CookieParam("userid") int userid,
                               @CookieParam("session") int session,
                               @FormParam("title") String title,
                               @FormParam("text") String text)
    {
        // "INSERT INTO posts(userid,title,text) VALUES (?,?,?)"
        //  SELECT @@IDENTITY as 'Identity'
        


        return Response.ok().build();
    }

}