package com.bluejay.server.common;

/**
 * Java representation of Reply
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
public class Reply extends Post {
	private int threadid;

	public int getThreadid() {
		return threadid;
	}

	public void setThreadid(int threadid) {
		this.threadid = threadid;
	}
}
