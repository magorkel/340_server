package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class PasswordDAO
{
    public boolean createPassword(String userName, String password)
    {
        //adds them both to the table and returns true if they were added ok
        return true;
    }

    public String findPassword(String userName)
    {
        //returns the password associated with the user
        return "tempPass";
    }
}