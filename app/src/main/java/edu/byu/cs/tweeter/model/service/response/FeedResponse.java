package edu.byu.cs.tweeter.model.service.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class FeedResponse extends PagedResponse
{
    private List<Status> posts;

    public FeedResponse(String message)
    {
        super(false, message, false);
    }

    public FeedResponse(List<Status> posts, boolean hasMorePages)
    {
        super(true, hasMorePages);
        this.posts = posts;
    }

    public List<Status> getFeed() { return posts; }
}
