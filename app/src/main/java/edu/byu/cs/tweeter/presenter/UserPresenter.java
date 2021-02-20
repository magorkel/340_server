package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.UserService;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
//This is a PARENT CLASS

public class UserPresenter {


    //A defualt constructor. Does nothing to the view.
    public UserPresenter() {
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public UserResponse getUser(UserRequest request) throws IOException {
        UserService userService = getUserService();
        return userService.getUser(request);
    }

    /**
     * Returns an instance of {@link FollowerService}. Allows mocking of the FollowingService class
     * for testing purposes. All usages of FollowingService should get their FollowingService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UserService getUserService() {
        return new UserService();
    }
}
