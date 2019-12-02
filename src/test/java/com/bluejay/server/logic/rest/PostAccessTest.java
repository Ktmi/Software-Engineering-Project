package com.bluejay.server.logic.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bluejay.server.common.Post;
import com.bluejay.server.common.Reply;
import com.bluejay.server.common.Thread;
import com.bluejay.server.common.User;
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

	@Mock
	private SecurityContext mockSecurityContext;

	/**
	 * Initializes mocks Basic setup for most tests.
	 */
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		postAccess = new PostAccess();
		postAccess.setDatabaseFacade(mockDatabaseFacade);
	}

	/**
	 * Cleans up after tests
	 */
	@After
	public void clean() {
		postAccess = null;
		mockDatabaseFacade = null;
	}

	/**
	 * Tests the getPost method for postAccess
	 */
	@Test
	public void testGetPost() throws Exception {
		Post post = new Post();

		/*
		 * getPost returns the object passed to it, due to the way it needs to pass
		 * information back to the client
		 */
		Post result = postAccess.getPost(post);

		/*
		 * postAccess.getPost calls databaseFacade.getPost on the arguments passed to it
		 */
		verify(mockDatabaseFacade).getPost(post);
		// Ensure that the object returned was the object passed to it
		assertSame("Made a new object rather than changing the existing one.", post, result);
	}

	/**
	 * Tests the getPost method for postAccess, for when databaseFacade throws an
	 * error.
	 */
	@Test
	public void testGetPostException() throws Exception {
		Post post = new Post();
		doThrow(new SQLException()).when(mockDatabaseFacade).getPost(post);

		Post result = postAccess.getPost(post);
		verify(mockDatabaseFacade).getPost(post);
		assertSame("Made a new object rather than changing the existing one.", post, result);

		assertEquals("Did not display proper message for error", "Content unavailable.", post.getContent());
	}

	/**
	 * Tests the getPosts method for postAccess
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPosts() throws Exception {
		/*
		 * Test setup:
		 * 
		 * Returns a list of posts, that will either contain only threads or only
		 * replies. We mock the functions that we would get these lists from.
		 * 
		 * 
		 */
		List<Post> threads = new ArrayList<Post>();
		List<Post> replies = new ArrayList<Post>();
		when(mockDatabaseFacade.getThreads(0, 50)).thenReturn(threads);
		when(mockDatabaseFacade.getReplies(any(Thread.class), eq(0), eq(50))).thenReturn(replies);

		List<Post> result;
		result = postAccess.getPosts(1, 0);

		assertSame("Result list not same as expected", threads, result);
		verify(mockDatabaseFacade).getThreads(0, 50);

		result = postAccess.getPosts(1, 1);

		assertSame("Result list not same as expected", replies, result);
		verify(mockDatabaseFacade).getReplies(any(Thread.class), eq(0), eq(50));
	}

	@Test
	public void testCreateThread() throws Exception {
		Thread thread = new Thread();
		thread.setUserid(0);
		User user = new User();
		int expectedUserid = 312;
		user.setUserid(expectedUserid);
		when(mockSecurityContext.getUserPrincipal()).thenReturn(user);

		Thread result = postAccess.createThread(thread, mockSecurityContext);

		verify(mockDatabaseFacade).createThread(thread);
		assertEquals("Userid of thread did not match expected", expectedUserid, thread.getUserid());
		assertSame("Made a new object rather than changing the existing one.", thread, result);
	}

	@Test
	public void testCreateReply() throws Exception {
		Reply reply = new Reply();
		reply.setUserid(0);
		User user = new User();
		int expectedUserid = 312;
		user.setUserid(expectedUserid);
		when(mockSecurityContext.getUserPrincipal()).thenReturn(user);

		Reply result = postAccess.createReply(reply, mockSecurityContext);
		verify(mockDatabaseFacade).createReply(reply);
		assertEquals("Userid of reply did not match expected", expectedUserid, reply.getUserid());
		assertSame("Made a new object rather than changing the existing one.", reply, result);
	}

}
