package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.service.LoginServiceImpl;
import edu.byu.cs.tweeter.server.service.LogoutServiceImpl;

public class GetLogoutHandler implements RequestHandler<LogoutRequest, LogoutResponse>
{
    @Override
    public LogoutResponse handleRequest(LogoutRequest logoutRequest, Context context)
    {
        LogoutServiceImpl logoutService = new LogoutServiceImpl();
        return logoutService.getLogout(logoutRequest);
    }
}
