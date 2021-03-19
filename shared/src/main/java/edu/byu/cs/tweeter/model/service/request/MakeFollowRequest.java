package edu.byu.cs.tweeter.model.service.request;

public class MakeFollowRequest
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

    public MakeFollowRequest(String followerAlias, String followeeAlias)
    {
        this.followerAlias = followerAlias;
        this.followerAlias = followerAlias;
    }

    public MakeFollowRequest() {}

    public void setFollowerAlias(String followerAlias)
    {
        this.followerAlias = followerAlias;
    }

    public void setFolloweeAlias(String followeeAlias)
    {
        this.followeeAlias = followeeAlias;
    }
}
