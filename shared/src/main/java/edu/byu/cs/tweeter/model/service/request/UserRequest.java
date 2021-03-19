package edu.byu.cs.tweeter.model.service.request;

public class UserRequest {
    private String userAlias; //to find the User


    public UserRequest(String userAlias) {
        this.userAlias = userAlias;
    }

    public UserRequest() {}

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias)
    {
        this.userAlias = userAlias;
    }
}
