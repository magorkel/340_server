package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;

public class PostRequest
{
    public Status getPostStatus()
    {
        return postStatus;
    }

    private Status postStatus;

    public PostRequest(Status postStatus)
    {
        this.postStatus = postStatus;
    }

    public PostRequest() {}

    public void setPostStatus(Status postStatus)
    {
        this.postStatus = postStatus;
    }
}
