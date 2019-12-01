package com.bluejay.server.logic.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bluejay.server.common.User;
import com.bluejay.server.db.DatabaseFacade;

public class UserAccessTest {

	@Mock
	private DatabaseFacade mockDatabaseFacade;

	private UserAccess userAccess;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		userAccess = new UserAccess();
		userAccess.setDatabaseFacade(mockDatabaseFacade);
	}

	@Test
	public void testWhoIs() throws Exception {
		User user = new User();

		User resultUser = userAccess.whoIs(user);
		verify(mockDatabaseFacade).getUser(user);

		assertSame("whoIs did not return same object passed to it.", user, resultUser);

	}

	@Test
	public void testCreateUser() throws Exception {
		User user = new User();

		Response response = userAccess.createUser(user);

		assertEquals("creatUser did not return redirect", "/bluejay-server/login",
				response.getHeaders().get("Location").get(0).toString());

	}

}
