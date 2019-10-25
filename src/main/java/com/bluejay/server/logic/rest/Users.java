package com.bluejay.server.logic.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/rest/user")
public class Users
{
    /**
     * Create a new user
     * @param userName User name
     * @param secret Password
     * @return Userid
     */
    @POST
    public Response createUser(@FormParam("username") String userName,
                               @FormParam("password") String secret)
    {
        // "INSERT INTO users(username, password, email) VALUES (?,?)"
        /*
        {
            "SUCCESS" : true,
            "userid": {userid}
        }
        OR
        {
            "SUCCESS" : false
        }
        */
        return Response.ok().build();
    }

    @GET
    @Path("/{userid}")
    public Response getUser(@PathParam("userid") int userid)
    {
        // "SELECT username FROM users WHERE userid = ?"
        /*
        {
            "userid":{userid},
            "username":"{username}"
        }
        */
        return Response.ok().build();
    }

    @GET
    @Path("/{userid}/posts")
    public Response getUserPosts(@PathParam("userid") int userid)
    {
        // "SELECT postid FROM posts WHERE userid = ?"
        /* {"userid": {userid},
            "posts":[postid]
           }
        */
        return Response.ok().build();
    }
}