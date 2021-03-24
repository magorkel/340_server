package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowerServiceProxy;
import edu.byu.cs.tweeter.model.service.UserServiceProxy;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
//This is a PARENT CLASS

public abstract class UserPresenter { //This class isn't initialized, so we will make it abstract. So it doesn't need its own.


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
    public UserResponse getUser(UserRequest request) throws IOException, TweeterRemoteException
    {
        UserServiceProxy userService = getUserService();
        return userService.getUser(request);
    }

    /**
     * Returns an instance of {@link FollowerServiceProxy}. Allows mocking of the FollowingService class
     * for testing purposes. All usages of FollowingService should get their FollowingService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UserServiceProxy getUserService() {
        return new UserServiceProxy();
    }
}
