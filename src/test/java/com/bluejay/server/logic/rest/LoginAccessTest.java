package com.bluejay.server.logic.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bluejay.server.common.User;
import com.bluejay.server.db.DatabaseFacade;
import com.bluejay.server.logic.Authentication;

/**
 * Tests loginAccess
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
public class LoginAccessTest {

	@Mock
	private DatabaseFacade mockDatabaseFacade;

	@Mock
	private Authentication mockAuthentication;

	private LoginAccess loginAccess;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		loginAccess = new LoginAccess();
		loginAccess.setDatabaseFacade(mockDatabaseFacade);
		loginAccess.setAuthentication(mockAuthentication);
	}

	@After
	public void clean() {
		mockDatabaseFacade = null;
		mockAuthentication = null;
		loginAccess = null;
	}

	@Test
	public void testLogin() {
		User user = new User();
		String expectedCookieValue = "Yo";
		when(mockAuthentication.issueToken(user)).thenReturn(expectedCookieValue);

		Response response = loginAccess.login(user);
		String result = response.getCookies().get("UserToken").getValue();

		assertEquals("Did not get expected cookie.", expectedCookieValue, result);

	}

	@Test
	public void testLoginException() throws Exception {
		User user = new User();
		doThrow(new SQLException()).when(mockDatabaseFacade).validateLogin(user);

		Response response = loginAccess.login(user);

		assertEquals("Did not return appropriate status.", Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}

}
