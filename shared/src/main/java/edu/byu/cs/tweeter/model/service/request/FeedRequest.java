package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;

public class FeedRequest
{
    private String username;
    private int limit;
    private Status lastStatus;


    public String getUsername()
    {
        return username;
    }

    public FeedRequest(String username, int limit, Status lastStatus)
    {
        this.username = username;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public FeedRequest() {}

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public void setLastStatus(Status lastStatus)
    {
        this.lastStatus = lastStatus;
    }

    public int getLimit() { return limit; }
    public Status getLastStatus() { return lastStatus; }
}
