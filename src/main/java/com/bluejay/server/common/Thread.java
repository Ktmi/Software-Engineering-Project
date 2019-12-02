package com.bluejay.server.common;

import javax.ws.rs.FormParam;

/**
 * Java representation of Thread
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
public class Thread extends Post {
	@FormParam("title")
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
