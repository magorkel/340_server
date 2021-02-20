package edu.byu.cs.tweeter.model.service.request;

public class UserRequest {
    private final String userAlias; //to find the User


    public UserRequest(String userAlias) {
        this.userAlias = userAlias;
    }

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public String getUserAlias() {
        return userAlias;
    }

}
