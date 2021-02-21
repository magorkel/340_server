package edu.byu.cs.tweeter.model.service.request;

public class MakeUnfollowRequest
{
    public String getFollowerAlias()
    {
        return followerAlias;
    }

    public String getFolloweeAlias()
    {
        return followeeAlias;
    }

    private String followerAlias;
    private String followeeAlias;

    public MakeUnfollowRequest(String followerAlias, String followeeAlias)
    {
        this.followerAlias = followerAlias;
        this.followerAlias = followerAlias;
    }
}
