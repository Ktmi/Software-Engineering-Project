package com.bluejay.server.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import com.bluejay.server.common.Post;
import com.bluejay.server.common.Reply;
import com.bluejay.server.common.Thread;
import com.bluejay.server.common.User;

/**
 * Provides an interface to the bluejay database.
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
public class DatabaseFacade {
	@Inject
	private DataSource ds;

	@Inject
	private MessageDigest encryption;

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
	}

	public DataSource getDataSource() {
		return this.ds;
	}

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

	/**
	 * 
	 * @param user
	 * @throws SQLException
	 */
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

	/**
	 * 
	 * @param thread Java representation of thread object to be added to database.
	 *               After operation is complete, this object is modified to include
	 *               the postid.
	 * 
	 * @throws SQLException
	 */
	public void createThread(Thread thread) throws SQLException {
		createPost(thread);
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement("INSERT INTO thread(postid,title) VALUES (?,?)");) {
			st.setInt(1, thread.getPostid());
			st.setString(2, thread.getTitle());
			st.executeUpdate();
		}
	}

	public void createReply(Reply reply) throws SQLException {
		createPost(reply);
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement("INSERT INTO reply(postid,threadid) VALUES (?,?)");) {
			st.setInt(1, reply.getPostid());
			st.setInt(2, reply.getThreadid());
			st.executeUpdate();
		}
	}

	/**
	 * Adds a post, with the give userid and content to the database. After adding
	 * the post to the database, the java object representing the post is updated to
	 * include the id of the post.
	 * 
	 * @param post Java representation of post object to be added to database. After
	 *             operation is complete, this object is modified to include the
	 *             postid.
	 * @throws SQLException
	 */
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

	/**
	 * TODO finish search back end
	 * 
	 * @param query
	 * @param orderBy
	 * @param from
	 * @param to
	 * @return
	 * @throws SQLException
	 */
	public List<Post> search(String query, List<String> orderBy, int from, int to) throws SQLException {
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM posts WHERE content LIKE ?");) {

		}
		return null;
	}

	protected final byte[] hashSecret(String secret) {
		encryption.update(secret.getBytes());
		return encryption.digest();
	}

}