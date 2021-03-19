package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;
import edu.byu.cs.tweeter.server.service.MakeUnfollowServiceImpl;

public class GetMakeUnfollowHandler implements RequestHandler<MakeUnfollowRequest, MakeUnfollowResponse> {
    @Override
    public MakeUnfollowResponse handleRequest(MakeUnfollowRequest request, Context context)
    {
        MakeUnfollowServiceImpl service = new MakeUnfollowServiceImpl();
        return service.updateUnfollowServer(request);
    }
}