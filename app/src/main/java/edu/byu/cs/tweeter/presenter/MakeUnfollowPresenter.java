package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.MakeUnfollowServiceProxy;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;

public class MakeUnfollowPresenter
{
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }
    public MakeUnfollowPresenter(View view) {
        this.view = view;
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public MakeUnfollowResponse sendUnfollowRequest(MakeUnfollowRequest request) throws IOException, TweeterRemoteException
    {
        MakeUnfollowServiceProxy makeUnfollowService = getMakeUnfollowService();
        return makeUnfollowService.updateUnfollowServer(request);
    }

    MakeUnfollowServiceProxy getMakeUnfollowService() {
        return new MakeUnfollowServiceProxy();
    }
}
