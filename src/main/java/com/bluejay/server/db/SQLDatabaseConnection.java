package com.bluejay.server.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SQLDatabaseConnection
{

    private Connection dbconnect;

    public SQLDatabaseConnection()
    throws SQLException, NamingException
    {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("jbdc/dbconnection");
        dbconnect = ds.getConnection();
    }


    public String getPost(int id) // TODO finish getPost
    throws SQLException
    {
        PreparedStatement st = dbconnect.prepareStatement("SELECT * FROM POSTS WHERE POSTS.id = ?");
        st.setInt(1,id);
        ResultSet rs = st.executeQuery();
        if(rs.next())
        {
            return rs.getString("");
        }
        return null;
    }

    /**
     * Verify that user session is valid.
     * @param user
     * @param token
     * @return returns true if user session is valid, false otherwise.
     */
    public boolean verifyToken(String user, String token) // TODO finish verifyToken
    {
        return false;
    }
    
    /**
     * 
     * @param user Username / Email
     * @param hash Hashed password.
     * @return Null if login invalid, otherwise, returns a session token for the user.
     */
    public String verifyLogin(String user, byte[] hash) // TODO finish verifyLogin
    {
        return null;
    }

}