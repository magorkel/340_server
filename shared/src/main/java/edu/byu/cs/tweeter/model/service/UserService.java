package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

import java.io.IOException;

public interface UserService {
    UserResponse getUser(UserRequest request)
            throws IOException, TweeterRemoteException;
}