package com.bluejay.server.common;

public class Reply
{
    private int threadid;
    private Post post;

    public int getThreadid()
    {
        return threadid;
    }

    public void setThreadid(int threadid)
    {
        this.threadid = threadid;
    }

    public Post getPost()
    {
        return post;
    }

    public void setPost(Post post)
    {
        this.post = post;
    }
}
