package com.bluejay.server.pages;

import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;

import com.bluejay.server.db.SQLDatabaseConnection;

public abstract class Page
extends HttpServlet
{
    /**
     * Here just so I can ignore the stupid warning.
     */
    private static final long serialVersionUID = 1L;
    
    protected SQLDatabaseConnection dbConnection = null;

    @Override
    public void init()
    throws ServletException {
        try
        {
            dbConnection = new SQLDatabaseConnection();
        }
        catch (Exception e)
        {
            ServletException e2 = new ServletException("Unable to initialize database connection.");
            e2.addSuppressed(e);
            throw e2;
        }
    }
    @Override
    public void destroy()
    {
        dbConnection = null;
    }

    public static final TreeMap<String,String> convertCookies (Cookie[] cookies)
    {
        TreeMap<String,String> cookieMap = new TreeMap<String,String>();
        for(Cookie cookie : cookies)
        {
            cookieMap.put(cookie.getName(), cookie.getValue());
        }
        return cookieMap;
    }
}