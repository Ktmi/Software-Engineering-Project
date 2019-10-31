package com.bluejay.server.db;

import javax.sql.DataSource;

import com.bluejay.server.logic.model.Login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SQLDatabaseConnection
implements AutoCloseable
{

    @Resource
    private Connection dbconnect;

    @Inject
    private MessageDigest md;

    public SQLDatabaseConnection()
    throws SQLException, NamingException
    {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("jbdc/dbconnection");
        dbconnect = ds.getConnection();
    }

    public boolean validateLogin(Login login)
    throws SQLException
    {
        PreparedStatement st = dbconnect.prepareStatement("SELECT * FROM user WHERE username = ? AND secret = ?");
        st.setString(1, login.getUsername());
        st.setBytes(2, hashSecret(login.getPassword()));
        ResultSet rs = st.executeQuery();
        return rs.next();
    }


    public boolean addAccount(Account )

    protected final byte[] hashSecret(String secret)
    throws SQLException
    {
        md.update(secret.getBytes());
        return md.digest();
    }

    @Override
    public void close() throws Exception {
        dbconnect.close();

    }

}