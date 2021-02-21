package edu.byu.cs.tweeter.model.service.response;

public class MakeUnfollowResponse extends Response
{
    public MakeUnfollowResponse(boolean success)
    {
        super(success);
    }

    public MakeUnfollowResponse(boolean success, String message)
    {
        super(success, message);
    }
}
