package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;

public class PostRequest
{
    public Status getPostStatus()
    {
        return postStatus;
    }

    private final Status postStatus;

    public PostRequest(Status postStatus)
    {
        this.postStatus = postStatus;
    }
}
