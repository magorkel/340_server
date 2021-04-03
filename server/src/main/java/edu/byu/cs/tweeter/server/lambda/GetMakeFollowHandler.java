package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;
import edu.byu.cs.tweeter.server.service.MakeFollowServiceImpl;

public class GetMakeFollowHandler implements RequestHandler<MakeFollowRequest, MakeFollowResponse> {
    @Override
    public MakeFollowResponse handleRequest(MakeFollowRequest request, Context context)
    {
        MakeFollowServiceImpl service = new MakeFollowServiceImpl();
        MakeFollowRequest makeFollowRequest = new MakeFollowRequest();
        return service.updateFollowServer(request);
    }
}