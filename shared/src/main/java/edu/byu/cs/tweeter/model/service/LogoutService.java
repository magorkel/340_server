package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

import java.io.IOException;

public interface LogoutService
{
    LogoutResponse getLogout(LogoutRequest request)
            throws IOException, TweeterRemoteException;
}
