package com.bluejay.server.logic.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	public void testGetThreads() throws Exception {
		List<Thread> threads = new ArrayList<Thread>();
		when(mockDatabaseFacade.getThreads(0, 50)).thenReturn(threads);

		List<Thread> result = postAccess.getThreads(1);

		assertSame("Result list not same as expected", threads, result);
		verify(mockDatabaseFacade).getThreads(0, 50);
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
