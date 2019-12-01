package com.bluejay.server.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bluejay.server.common.Post;
import com.bluejay.server.common.Reply;
import com.bluejay.server.common.Thread;
import com.bluejay.server.common.User;

/**
 * Unit tests for DatabaseFacade
 * 
 * @author David Ramirez <drami102@fiu.edu>
 *
 */
public class DatabaseFacadeTest {

	private DatabaseFacade databaseFacade;

	@Mock
	private DataSource mockDataSource;
	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockStatement;
	@Mock
	private ResultSet mockResultSet;

	private List<String> algorithms;

	private List<User> users;

	private List<Thread> threads;

	private List<Reply> replies;

	// Initialize the DatabaseFacade
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		databaseFacade = new DatabaseFacade();
		algorithms = new ArrayList<String>();
		users = new ArrayList<User>();
		threads = new ArrayList<Thread>();
		replies = new ArrayList<Reply>();

		algorithms.add("SHA-256");
		algorithms.add("MD5");
		algorithms.add("SHA-1");

		User user = new User();
		user.setUsername("David");
		user.setPassword("John Cena");
		user.setEmail("m@test.com");
		users.add(user);
		user = new User();
		user.setUsername("User2");
		user.setPassword("Password2");
		user.setEmail("t@test.com");

		Thread thread = new Thread();
		thread.setUserid(1);
		thread.setContent("Jo");
		thread.setTitle("Yo");
		threads.add(thread);

		Reply reply = new Reply();
		reply.setUserid(1);
		reply.setContent("Jo");
		reply.setThreadid(1);
		replies.add(reply);

		// Common stubs
		try {
			when(mockDataSource.getConnection()).thenReturn(mockConnection);
		} catch (SQLException e) {

		}
	}

	// Throw out the old database facade
	@After
	public void clean() {
		databaseFacade = null;
		algorithms = null;
		users = null;
		threads = null;
		replies = null;

		mockDataSource = null;
		mockConnection = null;
		mockStatement = null;
		mockResultSet = null;
	}

	@Test
	public void testGetSetDataSource() {
		// getEncryption returns null for just initialized databaseFacade
		assertNull("Default constructor initialized the MessageDigest.", databaseFacade.getDataSource());

		DataSource lastDataSource = null;

		for (int i = 0; i < 5; i++) {
			final DataSource mockedDataSource = mock(DataSource.class);
			assertNotNull("Mock returned null", mockedDataSource); // Check that return mock is not null
			assertNotSame("Duplicate mock return value", lastDataSource, mockedDataSource);

			databaseFacade.setDataSource(mockedDataSource);
			assertSame("Did not change DataSource", mockedDataSource, databaseFacade.getDataSource());
			lastDataSource = mockedDataSource;
		}
	}

	@Test
	public void testGetSetEncryptionMessageDigest() {
		// getEncryption returns null for just initialized databaseFacade
		assertNull("Default constructor initialized the MessageDigest.", databaseFacade.getEncryption());

		MessageDigest lastMessageDigest = null;

		for (int i = 0; i < 5; i++) {
			final MessageDigest mockedMessageDigest = mock(MessageDigest.class);
			assertNotNull("Mock returned null", mockedMessageDigest); // Check that return mock is not null
			assertNotSame("Duplicate mock return value", lastMessageDigest, mockedMessageDigest);

			databaseFacade.setEncryption(mockedMessageDigest);
			assertSame("Did not change MessageDigest", mockedMessageDigest, databaseFacade.getEncryption());
			lastMessageDigest = mockedMessageDigest;
		}
	}

	@Test
	public void testGetSetEncryptionString() {
		// getEncryption returns null for just initialized databaseFacade
		assertNull("Default constructor initialized the MessageDigest.", databaseFacade.getEncryption());

		MessageDigest result;
		MessageDigest lastResult = null;

		for (String algorithm : algorithms) {
			try {
				databaseFacade.setEncryption(algorithm);
			} catch (NoSuchAlgorithmException e) {
				fail("Failed to set encryption to specified algorithm.");
			}
			result = databaseFacade.getEncryption();

			assertNotSame("MessageDigest should have changed", lastResult, result);
			assertEquals("Message algorithm did not match expected", algorithm, result.getAlgorithm());
			lastResult = result;
		}
	}

	@Test(expected = NoSuchAlgorithmException.class)
	public void testSetEncryptionStringException() throws NoSuchAlgorithmException {
		databaseFacade.setEncryption("");
	}

	@Test
	public void testValidateLogin() throws Exception {
		// Setup stubs
		when(mockConnection.prepareStatement("SELECT userid FROM users WHERE username = ? AND secret = ?"))
				.thenReturn(mockStatement);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getInt(1)).thenReturn(1);

		databaseFacade.setDataSource(mockDataSource);

		for (String algorithm : algorithms) {
			MessageDigest expectedMessageDigest = MessageDigest.getInstance(algorithm);

			databaseFacade.setEncryption(algorithm);

			for (User user : users) {
				// Setup stubs, reset verify
				reset(mockStatement);
				when(mockStatement.executeQuery()).thenReturn(mockResultSet);

				String expectedName = user.getUsername();
				String expectedPassword = user.getPassword();
				user.setPassword(expectedPassword);

				databaseFacade.validateLogin(user);

				// Cleanup
				user.setPassword(expectedPassword);

				// Verify results
				verify(mockStatement).setString(1, expectedName);
				byte[] expectedHash = expectedMessageDigest.digest(expectedPassword.getBytes());
				verify(mockStatement).setBytes(2, expectedHash);

				assertEquals("Userid not set.", 1, user.getUserid());
			}
		}
	}

	@Test(expected = SQLException.class)
	public void testValidateLoginException() throws Exception {
		// Setup stubs
		when(mockConnection.prepareStatement("SELECT userid FROM users WHERE username = ? AND secret = ?"))
				.thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(false);

		databaseFacade.setDataSource(mockDataSource);
		databaseFacade.setEncryption("SHA-256");

		User temp = new User();
		temp.setUsername("");
		temp.setPassword("");

		databaseFacade.validateLogin(temp);
	}

	@Test
	public void testAddUser() throws Exception {
		// Setup stubs

		when(mockConnection.prepareStatement("INSERT INTO users(username,email,secret) VALUES (?,?,?)",
				Statement.RETURN_GENERATED_KEYS)).thenReturn(mockStatement);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getInt(1)).thenReturn(1);

		databaseFacade.setDataSource(mockDataSource);

		for (String algorithm : algorithms) {
			MessageDigest expectedMessageDigest = MessageDigest.getInstance(algorithm);

			databaseFacade.setEncryption(algorithm);

			for (User user : users) {
				// Setup stubs, reset verify
				reset(mockStatement);
				when(mockStatement.executeUpdate()).thenReturn(1);
				when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);

				String expectedName = user.getUsername();
				String expectedPassword = user.getPassword();
				String expectedEmail = user.getEmail();
				user.setPassword(expectedPassword);

				databaseFacade.addUser(user);

				// Cleanup
				user.setPassword(expectedPassword);

				// Verify results
				verify(mockStatement).setString(1, expectedName);
				verify(mockStatement).setString(2, expectedEmail);
				byte[] expectedHash = expectedMessageDigest.digest(expectedPassword.getBytes());
				verify(mockStatement).setBytes(3, expectedHash);

				assertEquals("Userid not set.", 1, user.getUserid());
			}
		}
	}

	@Test(expected = SQLException.class)
	public void testAddUserException() throws Exception {
		// Setup stubs
		when(mockConnection.prepareStatement("INSERT INTO users(username,email,secret) VALUES (?,?,?)",
				Statement.RETURN_GENERATED_KEYS)).thenReturn(mockStatement);
		when(mockStatement.executeUpdate()).thenReturn(0);

		databaseFacade.setDataSource(mockDataSource);
		databaseFacade.setEncryption("SHA-256");

		User temp = new User();
		temp.setUsername("");
		temp.setPassword("");
		temp.setEmail("");

		databaseFacade.addUser(temp);
	}

	@Test
	public void testCreateThread() throws Exception {

		PreparedStatement mockCreatePostStatement = mock(PreparedStatement.class);
		ResultSet mockCreatePostResultSet = mock(ResultSet.class);

		when(mockConnection.prepareStatement("INSERT INTO posts(userid,content) VALUES (?,?)",
				Statement.RETURN_GENERATED_KEYS)).thenReturn(mockCreatePostStatement);
		when(mockCreatePostStatement.executeUpdate()).thenReturn(1);
		when(mockCreatePostStatement.getGeneratedKeys()).thenReturn(mockCreatePostResultSet);
		when(mockCreatePostResultSet.next()).thenReturn(true);
		when(mockCreatePostResultSet.getInt(1)).thenReturn(1);

		when(mockConnection.prepareStatement("INSERT INTO threads(postid,title) VALUES (?,?)"))
				.thenReturn(mockStatement);

		databaseFacade.setDataSource(mockDataSource);

		for (Thread thread : threads) {

			databaseFacade.createThread(thread);
			assertEquals("", 1, thread.getPostid());
			verify(mockStatement).executeUpdate();
		}

	}

	@Test
	public void testCreateReply() throws Exception {

		PreparedStatement mockCreatePostStatement = mock(PreparedStatement.class);
		ResultSet mockCreatePostResultSet = mock(ResultSet.class);

		when(mockConnection.prepareStatement("INSERT INTO posts(userid,content) VALUES (?,?)",
				Statement.RETURN_GENERATED_KEYS)).thenReturn(mockCreatePostStatement);
		when(mockCreatePostStatement.executeUpdate()).thenReturn(1);
		when(mockCreatePostStatement.getGeneratedKeys()).thenReturn(mockCreatePostResultSet);
		when(mockCreatePostResultSet.next()).thenReturn(true);
		when(mockCreatePostResultSet.getInt(1)).thenReturn(1);

		databaseFacade.setDataSource(mockDataSource);

		when(mockConnection.prepareStatement("INSERT INTO replies(postid,threadid) VALUES (?,?)"))
				.thenReturn(mockStatement);

		for (Reply reply : replies) {

			databaseFacade.createReply(reply);
			assertEquals("", 1, reply.getPostid());
			verify(mockStatement).executeUpdate();
		}
	}

	/*
	 * @Test public void testCreatePost() { fail("Not yet implemented"); // TODO }
	 */

	@Test
	public void testGetPost() throws Exception {
		when(mockConnection.prepareStatement("SELECT userid, content FROM posts WHERE postid = ?"))
				.thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);

		when(mockResultSet.next()).thenReturn(true);

		when(mockResultSet.getInt(1)).thenReturn(1);
		String expectedContent = "Yo";
		when(mockResultSet.getString(2)).thenReturn(expectedContent);

		databaseFacade.setDataSource(mockDataSource);

		Post post = new Post();
		post.setPostid(1);

		databaseFacade.getPost(post);

		assertEquals("Returned content does not match", expectedContent, post.getContent());
	}

	@Test(expected = SQLException.class)
	public void testGetPostException() throws Exception {
		when(mockConnection.prepareStatement("SELECT userid, content FROM posts WHERE postid = ?"))
				.thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);

		when(mockResultSet.next()).thenReturn(false);

		databaseFacade.setDataSource(mockDataSource);

		Post post = new Post();
		post.setPostid(1);

		databaseFacade.getPost(post);
	}

	@Test
	public void testGetThreads() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetReplies() {
		fail("Not yet implemented"); // TODO
	}

	/*
	 * @Test public void testSearch() { fail("Not yet implemented"); // TODO }
	 */
}
