package edu.byu.cs.tweeter.model.service.response;

import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.User;

public class UserResponse extends Response {
    //These classes are for switching view between selected users in Feed, Story, Follows... Fragments.
    private User user;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public UserResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param user the followees to be included in the result.

     */
    public UserResponse(User user) {
        super(true);
        this.user = user;
    }

    public UserResponse() {
        super(false);
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * Returns the followers for the corresponding request.
     *
     * @return the followers.
     */
    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        UserResponse that = (UserResponse) param;

        return (Objects.equals(user, that.user) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
