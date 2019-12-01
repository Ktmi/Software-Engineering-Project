package com.bluejay.server.logic.rest;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bluejay.server.db.DatabaseFacade;

/**
 * Tests postAccess
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
public class PostAccessTest {

	private PostAccess postAccess;

	@Mock
	private DatabaseFacade mockDatabaseFacade;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		postAccess.setDatabaseFacade(mockDatabaseFacade);
	}

	@After
	public void clean() {
		postAccess = null;
		mockDatabaseFacade = null;
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
	public void testCreateThread() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCreateReply() {
		fail("Not yet implemented"); // TODO
	}

}
