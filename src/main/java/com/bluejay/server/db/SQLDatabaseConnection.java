package com.bluejay.server.db;

import javax.sql.DataSource;

import com.bluejay.server.logic.model.User;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.inject.Inject;

public class SQLDatabaseConnection
{

    @Inject
    private MessageDigest md;

    @Resource(name = "jbdc/bluejay-db")
    private DataSource ds;


    public void validateLogin(User user)
    throws SQLException
    {
        boolean result = false;
        try(Connection con = ds.getConnection();
            PreparedStatement st = con.prepareStatement("SELECT userid FROM user WHERE username = ? AND secret = ?");)
        {
            st.setString(1, user.getUsername());
            st.setBytes(2, hashSecret(user.getPassword()));
            ResultSet rs = st.executeQuery();
            if(rs.next())
            {
                user.setPassword(null);
                user.setUserid(rs.getInt(1));
            }
        }
    }

    // TODO finish addUser
    public void addUser(User user)
    throws SQLException
    {
        try(Connection con = ds.getConnection();
        PreparedStatement st = con.prepareStatement("INSERT INTO user(username,email,password) VALUES (?,?,?)");)
        {
            st.setString(1, user.getUsername());
            st.setString(2, user.getEmail());
            st.setBytes(3, hashSecret(user.getPassword()));
            if(st.executeUpdate() > 0)
            {
                ResultSet rs = st.getGeneratedKeys();
                rs.next();
                user.setUserid(rs.getInt(1));
                user.setPassword(null);
            }
        }
    }

    protected final byte[] hashSecret(String secret)
    {
        md.update(secret.getBytes());
        return md.digest();
    }

}