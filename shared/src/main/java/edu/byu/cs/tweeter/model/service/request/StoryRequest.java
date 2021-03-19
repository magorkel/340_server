package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryRequest
{
    private String username;
    private int limit;
    private Status lastStatus;


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
    public StoryRequest() {}

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
