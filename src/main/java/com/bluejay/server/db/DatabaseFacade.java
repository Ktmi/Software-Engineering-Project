package com.bluejay.server.db;

import javax.sql.DataSource;

import com.bluejay.server.common.User;
import com.bluejay.server.common.Post;
import com.bluejay.server.common.Thread;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

public class DatabaseFacade
{
    @Resource(name = "jbdc/bluejay-db")
    private DataSource ds;
    private MessageDigest encryption;

    public void setEncryption(MessageDigest encryption)
    {
        this.encryption = encryption;
    }
    public void setEncryption(String algorithm)
    throws NoSuchAlgorithmException
    {
        encryption = MessageDigest.getInstance(algorithm);
    }
    public MessageDigest getEncryption()
    {
        return encryption;
    }

    public void validateLogin(User user)
    throws SQLException
    {
        try(Connection con = ds.getConnection();
            PreparedStatement st = con.prepareStatement("SELECT userid FROM user WHERE username = ? AND secret = ?");)
        {
            st.setString(1, user.getUsername());
            st.setBytes(2, hashSecret(user.getPassword()));
            ResultSet rs = st.executeQuery();
            if(rs.next())
            {
                user.setUserid(rs.getInt(1));
            }
        }
    }
    
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
            }
        }
    }

    public void createPost(Thread thread)
    throws SQLException
    {
        try(Connection con = ds.getConnection();
        PreparedStatement st = con.prepareStatement("INSERT INTO posts(userid,content) VALUES (?,?)");
        PreparedStatement st2 = con.prepareStatement("INSERT INTO thread(postid,title) VALUES (?,?)");)
        {
            Post post = thread.getPost();
            st.setInt(1, post.getUserid());
            st.setString(2, post.getContent());
            if(st.executeUpdate() > 0)
            {
                ResultSet rs = st.getGeneratedKeys();
                rs.next();
                post.setUserid(rs.getInt(1));
            }
        }
    }




    protected final byte[] hashSecret(String secret)
    {
        encryption.update(secret.getBytes());
        return encryption.digest();
    }

}