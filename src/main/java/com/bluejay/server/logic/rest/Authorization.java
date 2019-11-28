package com.bluejay.server.logic.rest;

import java.security.Key;

import javax.crypto.KeyGenerator;
import javax.inject.Inject;

import com.bluejay.server.common.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Authorization {

	@Inject
	private KeyGenerator keyGenerator;

	public String issueToken(User user) {
		Key key = keyGenerator.generateKey();
		return Jwts.builder().setSubject(user.getUsername()).claim("username", user.getUsername())
				.claim("userid", user.getUserid()).signWith(SignatureAlgorithm.HS512, key).compact();
	}

	public User verifyToken(String token) throws Exception {
		Key key = keyGenerator.generateKey();
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		User user = new User();
		user.setUserid((int) claims.get("userid"));
		user.setUsername((String) claims.get("username"));
		return user;
	}

}