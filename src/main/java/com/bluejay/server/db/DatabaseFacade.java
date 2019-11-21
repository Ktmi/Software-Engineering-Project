package com.bluejay.server.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.bluejay.server.common.Post;
import com.bluejay.server.common.Reply;
import com.bluejay.server.common.Thread;
import com.bluejay.server.common.User;

public class DatabaseFacade {
	@Resource(name = "jbdc/bluejay-db")
	private DataSource ds;
	private MessageDigest encryption;

	public void setEncryption(MessageDigest encryption) {
		this.encryption = encryption;
	}

	public void setEncryption(String algorithm) throws NoSuchAlgorithmException {
		encryption = MessageDigest.getInstance(algorithm);
	}

	public MessageDigest getEncryption() {
		return encryption;
	}

	public void validateLogin(User user) throws SQLException {
		try (Connection con = ds.getConnection();
				PreparedStatement st = con
						.prepareStatement("SELECT userid FROM user WHERE username = ? AND secret = ?");) {
			st.setString(1, user.getUsername());
			st.setBytes(2, hashSecret(user.getPassword()));
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				user.setUserid(rs.getInt(1));
			}
		}
	}

	public void addUser(User user) throws SQLException {
		try (Connection con = ds.getConnection();
				PreparedStatement st = con
						.prepareStatement("INSERT INTO user(username,email,password) VALUES (?,?,?)");) {
			st.setString(1, user.getUsername());
			st.setString(2, user.getEmail());
			st.setBytes(3, hashSecret(user.getPassword()));
			if (st.executeUpdate() > 0) {
				ResultSet rs = st.getGeneratedKeys();
				rs.next();
				user.setUserid(rs.getInt(1));
			}
		}
	}

	public void createThread(Thread thread) throws SQLException {
		createPost(thread.getPost());
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement("INSERT INTO thread(postid,title) VALUES (?,?)");) {
			Post post = thread.getPost();
			st.setInt(1, post.getPostid());
			st.setString(2, thread.getTitle());
			st.executeUpdate();
		}
	}

	public void createReply(Reply reply) throws SQLException {
		createPost(reply.getPost());
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement("INSERT INTO reply(postid,threadid) VALUES (?,?)");) {
			Post post = reply.getPost();
			st.setInt(1, post.getPostid());
			st.setInt(2, reply.getThreadid());
			st.executeUpdate();
		}
	}

	protected void createPost(Post post) throws SQLException {
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement("INSERT INTO posts(userid,content) VALUES (?,?)");) {
			st.setInt(1, post.getUserid());
			st.setString(2, post.getContent());
			if (st.executeUpdate() > 0) {
				ResultSet rs = st.getGeneratedKeys();
				rs.next();
				post.setPostid(rs.getInt(1));
			}
		}

	}

	public List<Object> search(String query, List<String> orderBy, int from, int to) throws SQLException {
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM posts WHERE");) {

		}
		return null;
	}

	protected final byte[] hashSecret(String secret) {
		encryption.update(secret.getBytes());
		return encryption.digest();
	}

}