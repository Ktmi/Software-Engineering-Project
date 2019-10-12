package com.bluejay.server.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SQLDatabaseConnection {

    private Connection dbconnect;

    public SQLDatabaseConnection()
    throws SQLException, NamingException
    {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("jbdc/dbconnection");
        dbconnect = ds.getConnection();
    }
    public String getPost(int id)
    throws SQLException
    {
        PreparedStatement st = dbconnect.prepareStatement("SELECT * FROM ");
        st.setInt(1,id);
        ResultSet rs = st.executeQuery();
        return null;
    }
}