package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class AuthTokenDAO
{
    //create
    //find
    //delete
    public AuthToken createAuthToken(String userName)
    {
        //create a new random authToken and add it to the table associated with the userName
        AuthToken authToken = new AuthToken("hi");
        //AuthToken authToken = new AuthToken();
        return authToken;
    }

    public boolean findAuthToken(AuthToken authToken)
    {
        //is this a valid authToken? does it exist in the table - does it need to take in a user with it? so they match up?
        return true;
    }

    public boolean deleteAuthToken(AuthToken authToken)
    {
        //will it take in an AuthToken or a username
        return true;
    }
}
