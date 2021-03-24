package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LogoutRequest
{
    public LogoutRequest(User user)
    {
        this.user = user;
    }

    public LogoutRequest() {}

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    //nullify authtoken
    //send the user
    private User user;
}
