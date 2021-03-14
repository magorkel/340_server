package edu.byu.cs.tweeter.model.service.response;

import java.util.Objects;

public class MakeFollowResponse extends Response
{
    public MakeFollowResponse(boolean success)
    {
        super(success);
    }

    public MakeFollowResponse(boolean success, String message)
    {
        super(success, message);
    }
}
