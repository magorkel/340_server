package edu.byu.cs.tweeter.model.domain;

import java.util.List;

public class Story
{
    private List<Status> posts;
    private String username;

    public Story(List<Status> posts, String username)
    {
        this.posts = posts;
        this.username = username;
    }

    public Story(Status stat1, Status stat2, String username)
    {
        posts.add(stat1);
        posts.add(stat2);
        this.username = username;
    }

    public void setPost(Status post) { posts.add(post); }
    public List<Status> getPosts() { return posts; }
    public String getUsername() { return username; }
}
