package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

import java.io.IOException;

public interface StoryService {
    StoryResponse getStory(StoryRequest request)
            throws IOException, TweeterRemoteException;
}