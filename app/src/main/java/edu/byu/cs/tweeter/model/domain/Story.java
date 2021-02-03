package edu.byu.cs.tweeter.model.domain;

import java.util.List;

public class Story
{
    private List<Status> posts;

    public void setPost(Status post) { posts.add(post); }
    public List<Status> getPosts() { return posts; }
}
