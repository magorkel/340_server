package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class StoryResponse extends PagedResponse
{
    private User user;

    public StoryResponse(String message)
    {
        super(false, message, false);
    }

    public StoryResponse(User user, boolean hasMorePages)
    {
        super(true, hasMorePages);
        this.user = user;
    }

    public User getStory() { return user; }
}
