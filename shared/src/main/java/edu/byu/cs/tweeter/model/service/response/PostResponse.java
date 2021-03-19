package edu.byu.cs.tweeter.model.service.response;

public class PostResponse extends Response
{
    public PostResponse(boolean success)
    {
        super(success);
    }

    public PostResponse(boolean success, String message)
    {
        super(success, message);
    }

    public PostResponse(){
        super(false, "test");
    }
}
