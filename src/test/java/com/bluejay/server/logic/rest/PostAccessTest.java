package com.bluejay.server.logic.rest;

import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bluejay.server.common.Post;
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
		postAccess = new PostAccess();
		postAccess.setDatabaseFacade(mockDatabaseFacade);
	}

	@After
	public void clean() {
		postAccess = null;
		mockDatabaseFacade = null;
	}

	@Test
	public void testGetPost() throws Exception {
		Post post = new Post();

		postAccess.getPost(post);

		verify(mockDatabaseFacade).getPost(post);
	}

	@Test
	public void testGetThreads() {

	}

	@Test
	public void testCreateThread() {

	}

	@Test
	public void testCreateReply() {

	}

}
