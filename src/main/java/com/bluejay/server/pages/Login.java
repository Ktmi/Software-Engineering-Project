package com.bluejay.server.pages;

import java.io.IOException;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) // TODO finish doPost for Login
    throws ServletException, IOException
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(req.getParameter("password").getBytes());
            byte[] byteStore = md.digest();
        }
        catch(Exception e)
        {
            throw new ServletException();
        }
    }


}