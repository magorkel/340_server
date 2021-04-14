package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;

import java.io.IOException;

public class LogoutServiceImpl implements LogoutService
{
    @Override
    public LogoutResponse getLogout(LogoutRequest request)
    {
        //FIXME need to get userName?
        //can we store the userName inside the user object?
        System.out.println("Trying to print" + request.getUser().getAlias());
        boolean authTokenRemoved = getAuthTokenDAO().deleteAuthToken(request.getUser().getAlias());

        if (authTokenRemoved)
        {
            //AuthToken authToken = getAuthTokenDAO().createAuthToken(request.getUsername());
            return new LogoutResponse(true);
        }
        else
        {
            return new LogoutResponse(false, "Could not logout (authToken not found)");
        }
    }

    AuthTokenDAO getAuthTokenDAO()
    {
        return new AuthTokenDAO();
    }

}
