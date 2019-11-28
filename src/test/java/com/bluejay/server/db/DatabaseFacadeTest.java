package com.bluejay.server.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseFacadeTest {

	private DatabaseFacade databaseFacade;

	// Initialize the DatabaseFacade
	@Before
	public void init() {
		databaseFacade = new DatabaseFacade();
	}

	// Throw out the old database facade
	@After
	public void clean() {
		databaseFacade = null;
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
	public void testGetSetEncryptionString() throws NoSuchAlgorithmException {
		// getEncryption returns null for just initialized databaseFacade
		assertNull("Default constructor initialized the MessageDigest.", databaseFacade.getEncryption());

		MessageDigest result;
		MessageDigest lastResult = null;

		List<String> algorithms = new ArrayList();
		algorithms.add("SHA-256");
		algorithms.add("MD5");
		algorithms.add("SHA-1");

		for (String algorithm : algorithms) {
			databaseFacade.setEncryption(algorithm);
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
	public void testValidateLogin() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddUser() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCreateThread() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCreateReply() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCreatePost() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetPost() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetThreads() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetReplies() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSearch() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testHashSecret() {
		fail("Not yet implemented"); // TODO
	}

}
