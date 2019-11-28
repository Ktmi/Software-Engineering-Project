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
		// getDataSource returns null for just initialized databaseFacade
		assertNull("Default constructor initialized the DatasSurce.", databaseFacade.getDataSource());

		final DataSource mockedDataSource1 = mock(DataSource.class);
		assertNotNull("Mock returned null", mockedDataSource1); // Check that return mock is not null

		databaseFacade.setDataSource(mockedDataSource1);
		assertSame("Did not change DataSource", mockedDataSource1, databaseFacade.getDataSource());

		final DataSource mockedDataSource2 = mock(DataSource.class);
		assertNotNull("Mock returned null", mockedDataSource2);// Check that return mock is not null
		assertNotSame("Duplicate mocks return value", mockedDataSource1, mockedDataSource2);

		databaseFacade.setDataSource(mockedDataSource2);
		assertSame("Did not change DataSource.", mockedDataSource2, databaseFacade.getDataSource());
	}

	@Test
	public void testGetSetEncryptionMessageDigest() {
		// getEncryption returns null for just initialized databaseFacade
		assertNull("Default constructor initialized the MessageDigest.", databaseFacade.getEncryption());

		final MessageDigest mockedMessageDigest1 = mock(MessageDigest.class);
		assertNotNull("Mock returned null", mockedMessageDigest1); // Check that return mock is not null

		databaseFacade.setEncryption(mockedMessageDigest1);
		assertSame("Did not change MessageDigest", mockedMessageDigest1, databaseFacade.getEncryption());

		final MessageDigest mockedMessageDigest2 = mock(MessageDigest.class);
		assertNotNull("Mock returned null", mockedMessageDigest2);// Check that return mock is not null
		assertNotSame("Duplicate mocks return value", mockedMessageDigest1, mockedMessageDigest2);

		databaseFacade.setEncryption(mockedMessageDigest2);
		assertSame("Did not change MessageDigest", mockedMessageDigest2, databaseFacade.getEncryption());
	}

	@Test
	public void testSetEncryptionString() throws NoSuchAlgorithmException {
		// getEncryption returns null for just initialized databaseFacade
		assertNull("Default constructor initialized the MessageDigest.", databaseFacade.getEncryption());

		String algorithm;
		MessageDigest result;
		MessageDigest lastResult = null;

		algorithm = "SHA-256";
		databaseFacade.setEncryption(algorithm);
		result = databaseFacade.getEncryption();

		assertNotSame("MessageDigest should have changed", lastResult, result);
		assertEquals("Message algorithm did not match expected", algorithm, result.getAlgorithm());
		lastResult = result;

		algorithm = "MD5";
		databaseFacade.setEncryption(algorithm);
		result = databaseFacade.getEncryption();

		assertNotSame("MessageDigest should have changed", lastResult, result);
		assertEquals("Message algorithm did not match expected", algorithm, result.getAlgorithm());

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
