package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class MakeFollowService
{
    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public MakeFollowResponse sendFollowRequest(MakeFollowRequest request) throws IOException, TweeterRemoteException
    {
        MakeFollowResponse response = getServerFacade().updateFollowServer(request);

        /*if(response.isSuccess()) {
            //loadImages(response);
            return respon
        }*/

        return response;
    }

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */ //FIXME: Add checking to confirm that url is existant before running this, else grab directly from url.
    /*private void loadImages(UserResponse response) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(response.getUser().getImageUrl());
        response.getUser().setImageBytes(bytes);
    }*/

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
