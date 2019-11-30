package com.bluejay.server.logic;

import java.security.Key;
import java.util.Calendar;

import javax.inject.Inject;

import com.bluejay.server.common.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Create and verify signed keys for user authentication.
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
public class Authentication {

	@Inject
	private Key key;

	@Inject
	private SignatureAlgorithm signatureAlgorithm;

	public void setKey(Key key) {
		this.key = key;
	}

	public String issueToken(User user) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONDAY, 1);
		return Jwts.builder().setSubject(user.getUsername()).claim("username", user.getUsername())
				.claim("userid", user.getUserid()).signWith(signatureAlgorithm, key).setExpiration(cal.getTime())
				.compact();
	}

	public User verifyToken(String token) throws JwtException {
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		User user = new User();
		user.setUserid((int) claims.get("userid"));
		user.setUsername((String) claims.get("username"));
		return user;
	}

}