package com.bluejay.server.logic;

import static org.junit.Assert.fail;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.junit.Before;
import org.junit.Test;

import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationTest {

	private Authentication authentication;

	private Key key;

	private SignatureAlgorithm signatureAlgorithm;

	@Before
	public void init() {
		byte[] secretBytes = { 1, 2, 3 };
		signatureAlgorithm = SignatureAlgorithm.HS512;
		key = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());

	}

	@Test
	public void testIssueToken() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testVerifyToken() {
		fail("Not yet implemented"); // TODO
	}

}
