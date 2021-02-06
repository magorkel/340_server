package edu.byu.cs.tweeter.model.service.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class StoryResponse extends PagedResponse
{
    private List<Status> posts;

    public StoryResponse(String message)
    {
        super(false, message, false);
    }

    public StoryResponse(List<Status> posts, boolean hasMorePages)
    {
        super(true, hasMorePages);
        this.posts = posts;
    }

    public List<Status> getStory() { return posts; }
}
