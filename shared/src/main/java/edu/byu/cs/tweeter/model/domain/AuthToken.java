package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
    //don't need to implement until milestone 4
    private String authToken;

    public AuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    public AuthToken() {}

    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }
}
