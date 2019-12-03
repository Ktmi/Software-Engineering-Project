package com.bluejay.server.common;

import javax.ws.rs.FormParam;

/**
 * Java representation of Post
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
public class Post {
	private int postid;
	private int userid;
	@FormParam("content")
	private String content;

	public int getPostid() {
		return postid;
	}

	public void setPostid(int postid) {
		this.postid = postid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
