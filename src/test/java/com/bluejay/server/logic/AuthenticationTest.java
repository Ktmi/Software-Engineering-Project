package com.bluejay.server.logic;

import static org.junit.Assert.assertEquals;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bluejay.server.common.User;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * Tests the Authentication provider
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
public class AuthenticationTest {

	private Authentication authentication;

	private Key key;

	private SignatureAlgorithm signatureAlgorithm;

	private User testUser;

	@Before
	public void init() {
		byte[] secretBytes = { 1, 2, 3 };
		signatureAlgorithm = SignatureAlgorithm.HS512;
		key = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());
		authentication = new Authentication();
		authentication.setKey(key);
		authentication.setSignatureAlgorithm(signatureAlgorithm);

		testUser = new User();
		testUser.setUserid(34);
		testUser.setUsername("Poi");

	}

	@After
	public void clean() {
		key = null;
		authentication = null;
		signatureAlgorithm = null;
		testUser = null;
	}

	// We don't necessarily care how the components work individually, we care how
	// they work together
	@Test
	public void testIssueVerifyToken() {

		String token = authentication.issueToken(testUser);
		User resultUser = authentication.verifyToken(token);
		assertEquals("userid did not match", testUser.getUserid(), resultUser.getUserid());
		assertEquals("username did not match", testUser.getUsername(), resultUser.getUsername());
	}

	@Test(expected = SignatureException.class)
	public void testForeignSignatureToken() {
		byte[] foreignSecretBytes = { 1, 2, 2 };
		Key foreignKey = new SecretKeySpec(foreignSecretBytes, signatureAlgorithm.getJcaName());
		Authentication foreignAuthentication = new Authentication();
		foreignAuthentication.setSignatureAlgorithm(signatureAlgorithm);
		foreignAuthentication.setKey(foreignKey);

		String foreignToken = foreignAuthentication.issueToken(testUser);
		authentication.verifyToken(foreignToken);
	}

	/*
	 * @Test public void testIssueToken() { fail("Not yet implemented");}
	 * 
	 * @Test public void testVerifyToken() { fail("Not yet implemented");}
	 */
}
