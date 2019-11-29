package com.bluejay.server.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	private DataSource ds;

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
						.prepareStatement("SELECT userid FROM users WHERE username = ? AND secret = ?");) {
			st.setString(1, user.getUsername());
			st.setBytes(2, hashSecret(user.getPassword()));
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				user.setUserid(rs.getInt(1));
			} else {
				throw new SQLException();
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
						.prepareStatement("INSERT INTO users(username,email,password) VALUES (?,?,?)");) {
			st.setString(1, user.getUsername());
			st.setString(2, user.getEmail());
			st.setBytes(3, hashSecret(user.getPassword()));
			if (st.executeUpdate() > 0) {
				ResultSet rs = st.getGeneratedKeys();
				rs.next();
				user.setUserid(rs.getInt(1));
			} else {
				throw new SQLException();
			}
		}
	}

	public void getUser(User user) throws SQLException {
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT username FROM users WHERE userid = ?");) {
			st.setInt(1, user.getUserid());
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				user.setUserid(rs.getInt(1));
			} else {
				throw new SQLException();
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
				PreparedStatement st = con.prepareStatement("INSERT INTO threads(postid,title) VALUES (?,?)");) {
			st.setInt(1, thread.getPostid());
			st.setString(2, thread.getTitle());
			st.executeUpdate();
		}
	}

	public void createReply(Reply reply) throws SQLException {
		createPost(reply);
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement("INSERT INTO replies(postid,threadid) VALUES (?,?)");) {
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
	 * 
	 * 
	 * @param post Input and output parameter. Expected to have a valid postid
	 *             assigned. After function completion the content and userid is
	 *             written to this object.
	 * @throws SQLException
	 */
	public void getPost(Post post) throws SQLException {
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT userid, content FROM posts WHERE postid = ?");) {
			st.setInt(1, post.getPostid());
			try (ResultSet rs = st.executeQuery();) {
				if (rs.next()) {
					post.setUserid(rs.getInt(1));
					post.setContent(rs.getString(2));
				} else
					throw new SQLException();
			}
		}
	}

	public List<Thread> getThreads(int page, int amt) throws SQLException {
		List<Thread> list = new ArrayList<Thread>();
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement(
						"SELECT userid, posts.postid, title FROM threads INNER JOIN posts LIMIT ?,?");) {
			st.setInt(1, page * amt);
			st.setInt(2, amt);
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					Thread temp = new Thread();
					temp.setUserid(rs.getInt(1));
					temp.setPostid(rs.getInt(2));
					temp.setTitle(rs.getString(3));
					list.add(temp);
				}
			}
		}
		return list;
	}

	public List<Reply> getReplies(Thread thread, int page, int amt) throws SQLException {
		List<Reply> list = new ArrayList<Reply>();
		try (Connection con = ds.getConnection();
				PreparedStatement st = con.prepareStatement(
						"SELECT userid, postid FROM replies INNER JOIN posts WHERE threadid = ? LIMIT ?,?");) {
			st.setInt(1, thread.getPostid());
			st.setInt(2, page * amt);
			st.setInt(3, amt);
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					Reply temp = new Reply();
					temp.setUserid(rs.getInt(1));
					temp.setPostid(rs.getInt(2));
					list.add(temp);
				}
			}
		}
		return list;
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