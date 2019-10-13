package com.bluejay.server.pages;

import java.util.TreeMap;

import javax.servlet.http.Cookie;

public class Utilities
{

    private Utilities() {}

    public static TreeMap<String,String> convertCookies (Cookie[] cookies)
    {
        TreeMap<String,String> cookieMap = new TreeMap<String,String>();
        for(Cookie cookie : cookies)
        {
            cookieMap.put(cookie.getName(), cookie.getValue());
        }
        return cookieMap;
    }

}