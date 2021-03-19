package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;

import java.io.IOException;

public interface PostService {
    PostResponse updatePostServer(PostRequest request)
            throws IOException, TweeterRemoteException;
}