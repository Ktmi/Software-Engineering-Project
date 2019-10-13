package com.bluejay.server.pages;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/test2")
public class Login
extends HttpServlet
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) // TODO finish doPost for Login
    throws ServletException, IOException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(req.getParameter("password").getBytes());
        byte[] byteStore = md.digest();
        
    }


}