package com.bluejay.server.logic.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test2")
public class Login
extends Page
{
    
    /**
     * Here just so I can ignore the stupid warning.
     */
    private static final long serialVersionUID = 1L;



    // TODO finish doPost for Login
    //   - Needs to finish db component
    //
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
    throws ServletException, IOException
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch(NoSuchAlgorithmException e)
        {
            ServletException e2 = new ServletException("Unable to process login.");
            e2.addSuppressed(e);
            throw e2;
        }
        md.update(req.getParameter("password").getBytes());
        String session = dbConnection.verifyLogin(req.getParameter("username"), md.digest());

        PrintWriter out = resp.getWriter();
        if (session == null)
        {
            out.println("{\"SUCCESS\": false}");
            return;
        }
        Cookie userCookie = new Cookie("username", req.getParameter("username"));
        userCookie.setMaxAge(60 * 60 * 30);
        Cookie sessionCookie = new Cookie("session", session);
        sessionCookie.setMaxAge(60 * 60 * 30);
        resp.addCookie(userCookie);
        resp.addCookie(sessionCookie);
        out.println("{\"SUCCESS\": true}");
    }


}