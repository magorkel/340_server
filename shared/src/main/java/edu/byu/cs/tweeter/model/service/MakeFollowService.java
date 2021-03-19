package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;

import java.io.IOException;

public interface MakeFollowService {
    MakeFollowResponse updateFollowServer(MakeFollowRequest request)
            throws IOException, TweeterRemoteException;
}