package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;

import java.io.IOException;

public interface MakeUnfollowService {
    MakeUnfollowResponse updateUnfollowServer(MakeUnfollowRequest request)
            throws IOException, TweeterRemoteException;
}