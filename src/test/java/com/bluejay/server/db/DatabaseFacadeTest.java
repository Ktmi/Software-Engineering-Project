package com.bluejay.server.db;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.MessageDigest;
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

		databaseFacade.setDataSource(mockDataSource);

		// Common stubs
		try {

			when(mockDataSource.getConnection()).thenReturn(mockConnection);

			when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

			when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
					.thenReturn(mockStatement);

			when(mockStatement.executeQuery()).thenReturn(mockResultSet);

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

	/*
	 * Old test cases, mostly useless for the moment.
	 */

	/*
	 * @Test public void testGetSetDataSource() { // getEncryption returns null for
	 * just initialized databaseFacade
	 * assertNull("Default constructor initialized the MessageDigest.",
	 * databaseFacade.getDataSource());
	 * 
	 * DataSource lastDataSource = null;
	 * 
	 * for (int i = 0; i < 5; i++) { final DataSource mockedDataSource =
	 * mock(DataSource.class); assertNotNull("Mock returned null",
	 * mockedDataSource); // Check that return mock is not null
	 * assertNotSame("Duplicate mock return value", lastDataSource,
	 * mockedDataSource);
	 * 
	 * databaseFacade.setDataSource(mockedDataSource);
	 * assertSame("Did not change DataSource", mockedDataSource,
	 * databaseFacade.getDataSource()); lastDataSource = mockedDataSource; } }
	 * 
	 * @Test public void testGetSetEncryptionMessageDigest() { // getEncryption
	 * returns null for just initialized databaseFacade
	 * assertNull("Default constructor initialized the MessageDigest.",
	 * databaseFacade.getEncryption());
	 * 
	 * MessageDigest lastMessageDigest = null;
	 * 
	 * for (int i = 0; i < 5; i++) { final MessageDigest mockedMessageDigest =
	 * mock(MessageDigest.class); assertNotNull("Mock returned null",
	 * mockedMessageDigest); // Check that return mock is not null
	 * assertNotSame("Duplicate mock return value", lastMessageDigest,
	 * mockedMessageDigest);
	 * 
	 * databaseFacade.setEncryption(mockedMessageDigest);
	 * assertSame("Did not change MessageDigest", mockedMessageDigest,
	 * databaseFacade.getEncryption()); lastMessageDigest = mockedMessageDigest; } }
	 * 
	 * @Test public void testGetSetEncryptionString() { // getEncryption returns
	 * null for just initialized databaseFacade
	 * assertNull("Default constructor initialized the MessageDigest.",
	 * databaseFacade.getEncryption());
	 * 
	 * MessageDigest result; MessageDigest lastResult = null;
	 * 
	 * for (String algorithm : algorithms) { try {
	 * databaseFacade.setEncryption(algorithm); } catch (NoSuchAlgorithmException e)
	 * { fail("Failed to set encryption to specified algorithm."); } result =
	 * databaseFacade.getEncryption();
	 * 
	 * assertNotSame("MessageDigest should have changed", lastResult, result);
	 * assertEquals("Message algorithm did not match expected", algorithm,
	 * result.getAlgorithm()); lastResult = result; } }
	 *
	 * 
	 * @Test(expected = NoSuchAlgorithmException.class) public void
	 * testSetEncryptionStringException() throws NoSuchAlgorithmException {
	 * databaseFacade.setEncryption(""); }
	 */

	@Test
	public void testValidateLogin() throws Exception {
		// Setup stubs
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getInt(1)).thenReturn(1);

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
				user.setUserid(0);
			}
		}
	}

	@Test(expected = SQLException.class)
	public void testValidateLoginException() throws Exception {
		// Setup stubs

		when(mockResultSet.next()).thenReturn(false);

		databaseFacade.setEncryption("SHA-256");

		User temp = new User();
		temp.setUsername("");
		temp.setPassword("");

		databaseFacade.validateLogin(temp);
	}

	@Test
	public void testAddUser() throws Exception {
		// Setup stubs

		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getInt(1)).thenReturn(1);

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
				user.setUserid(0);
			}
		}
	}

	@Test(expected = SQLException.class)
	public void testAddUserException() throws Exception {
		// Setup stubs
		when(mockStatement.executeUpdate()).thenReturn(0);

		databaseFacade.setEncryption("SHA-256");

		User temp = new User();
		temp.setUsername("");
		temp.setPassword("");
		temp.setEmail("");

		databaseFacade.addUser(temp);
	}

	@Test
	public void testCreateThread() throws Exception {
		prepareCreatePost();

		for (Thread thread : threads) {

			databaseFacade.createThread(thread);
			assertEquals("Postid does not match expected", 1, thread.getPostid());
			verify(mockStatement).executeUpdate();
			thread.setPostid(0);
		}

	}

	@Test
	public void testCreateReply() throws Exception {
		prepareCreatePost();

		for (Reply reply : replies) {

			databaseFacade.createReply(reply);
			assertEquals("Postid does not match expected", 1, reply.getPostid());
			verify(mockStatement).executeUpdate();
			reply.setPostid(0);
		}
	}

	private void prepareCreatePost() throws Exception {
		PreparedStatement mockCreatePostStatement = mock(PreparedStatement.class);
		ResultSet mockCreatePostResultSet = mock(ResultSet.class);

		when(mockConnection.prepareStatement("INSERT INTO posts(userid,content) VALUES (?,?)",
				Statement.RETURN_GENERATED_KEYS)).thenReturn(mockCreatePostStatement);
		when(mockCreatePostStatement.executeUpdate()).thenReturn(1);
		when(mockCreatePostStatement.getGeneratedKeys()).thenReturn(mockCreatePostResultSet);
		when(mockCreatePostResultSet.next()).thenReturn(true);
		when(mockCreatePostResultSet.getInt(1)).thenReturn(1);
	}

	/*
	 * @Test public void testCreatePost() { fail("Not yet implemented");}
	 */

	@Test
	public void testGetPost() throws Exception {

		when(mockResultSet.next()).thenReturn(true);

		when(mockResultSet.getInt(1)).thenReturn(1);
		String expectedContent = "Yo";
		when(mockResultSet.getString(2)).thenReturn(expectedContent);

		Post post = new Post();
		post.setPostid(1);

		databaseFacade.getPost(post);

		assertEquals("Returned content does not match", expectedContent, post.getContent());
		assertEquals("Returned userid does not match", 1, post.getUserid());
	}

	@Test(expected = SQLException.class)
	public void testGetPostException() throws Exception {

		when(mockResultSet.next()).thenReturn(false);

		Post post = new Post();
		post.setPostid(1);

		databaseFacade.getPost(post);
	}

	@Test
	public void testGetThreads() throws Exception {

		when(mockResultSet.next()).thenReturn(true).thenReturn(false);

		int expectedUserid = 1;
		int expectedPostid = 2;
		String expectedTitle = "Yo";

		when(mockResultSet.getInt(1)).thenReturn(expectedUserid);
		when(mockResultSet.getInt(2)).thenReturn(expectedPostid);
		when(mockResultSet.getString(3)).thenReturn(expectedTitle);

		List<Post> result = databaseFacade.getThreads(1, 1);

		Thread resultThread = (Thread) result.get(0);

		assertEquals("Did not return the expected amount of threads", 1, result.size());
		assertEquals("Did not return expected userid", expectedUserid, resultThread.getUserid());
		assertEquals("Did not return expected postid", expectedPostid, resultThread.getPostid());
		assertEquals("Did not return expected title", expectedTitle, resultThread.getTitle());

	}

	@Test
	public void testGetReplies() throws Exception {

		when(mockResultSet.next()).thenReturn(true).thenReturn(false);

		int expectedUserid = 1;
		int expectedPostid = 2;
		int expectedThreadid = 3;

		when(mockResultSet.getInt(1)).thenReturn(expectedUserid);
		when(mockResultSet.getInt(2)).thenReturn(expectedPostid);

		Thread thread = new Thread();

		thread.setPostid(expectedThreadid);

		List<Post> result = databaseFacade.getReplies(thread, 1, 1);

		Reply resultReply = (Reply) result.get(0);

		assertEquals("Did not return the expected amount of threads", 1, result.size());
		assertEquals("Did not return expected userid", expectedUserid, resultReply.getUserid());
		assertEquals("Did not return expected postid", expectedPostid, resultReply.getPostid());
		assertEquals("Did not return expected title", expectedThreadid, resultReply.getThreadid());

	}

	@Test
	public void testGetUser() throws Exception {

		when(mockResultSet.next()).thenReturn(true);

		String expectedUsername = "Yo";

		when(mockResultSet.getString(1)).thenReturn(expectedUsername);

		User user = new User();
		user.setUserid(1);

		databaseFacade.getUser(user);

		assertEquals("Did not return expected username", expectedUsername, user.getUsername());

	}

	@Test(expected = SQLException.class)
	public void testGetUserException() throws Exception {

		when(mockResultSet.next()).thenReturn(false);

		User user = new User();
		user.setUserid(1);

		databaseFacade.getUser(user);
	}

	/*
	 * @Test public void testSearch() { fail("Not yet implemented");}
	 */
}
