package edu.byu.cs.tweeter.model.service.request;

public class StoryRequest
{
    private final String username;

    public String getUsername()
    {
        return username;
    }

    public StoryRequest(String username)
    {
        this.username = username;
    }
}
