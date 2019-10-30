package com.bluejay.server.db;

import javax.sql.DataSource;

import com.bluejay.server.logic.model.Login;
import com.bluejay.server.logic.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SQLDatabaseConnection
implements AutoCloseable
{

    private Connection dbconnect;
    private String encryptionType;



    public SQLDatabaseConnection()
    throws SQLException, NamingException
    {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("jbdc/dbconnection");
        dbconnect = ds.getConnection();
        setEncryptionType("SHA-256");
    }

    public boolean validateLogin(Login login)
    throws SQLException
    {
        boolean result = false;
        try
        {
            PreparedStatement st = dbconnect.prepareStatement("SELECT * FROM user WHERE username = ? AND secret = ?");
            st.setString(1, login.getUsername());
            st.setBytes(2, hashSecret(login.getPassword()));
            ResultSet rs = st.executeQuery();
            result = rs.next();
        }
        catch(NoSuchAlgorithmException e)
        {
            SQLException e2 = new SQLException("Unable to load hashing algorithm.");
            e2.addSuppressed(e);
            throw e2;
        }
        return result;
    }



    // TODO finish addUser
    public boolean addUser(User user)
    throws SQLException
    {
        PreparedStatement st = dbconnect.prepareStatement("sql");


        return true;
    }




    protected final void setEncryptionType(String encryptionType)
    {
        this.encryptionType = encryptionType;
    }

    protected final String getEncryptionType()
    {
        return encryptionType;
    }

    protected final byte[] hashSecret(String secret)
    throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance(getEncryptionType());
        md.update(secret.getBytes());
        return md.digest();
    }

    @Override
    public void close() throws Exception {
        dbconnect.close();

    }

}