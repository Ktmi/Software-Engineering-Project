package com.bluejay.server.common;

import java.security.Principal;

import javax.ws.rs.FormParam;

/**
 * Java representation of User
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
public class User implements Principal {
	private int userid;
	@FormParam("username")
	private String username;
	@FormParam("password")
	private String password;
	private String email;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		String temp = this.password;
		this.password = null;
		return temp;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getName() {
		return userid + '|' + username;
	}

}
