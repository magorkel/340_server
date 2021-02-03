package edu.byu.cs.tweeter.model.domain;

import java.util.List;

public class Feed
{
    private List<Status> stories;

    public void setStories(Status post) { stories.add(post); }
    public List<Status> getStories() { return stories; }
}
