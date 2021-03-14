package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;

public class StoryRequest
{
    private final String username;
    private final int limit;
    private final Status lastStatus;


    public String getUsername()
    {
        return username;
    }

    public StoryRequest(String username, int limit, Status lastStatus)
    {
        this.username = username;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public int getLimit() { return limit; }
    public Status getLastStatus() { return lastStatus; }
}
