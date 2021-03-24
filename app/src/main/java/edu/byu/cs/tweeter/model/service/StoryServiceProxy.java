package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class StoryServiceProxy implements StoryService
{
    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException
    {
        StoryResponse response = getServerFacade().getStory(request);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(StoryResponse response) throws IOException {
        for(Status status : response.getPosts()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.getUser().setImageBytes(bytes);
        }
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
