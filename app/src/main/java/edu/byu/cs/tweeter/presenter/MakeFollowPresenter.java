package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowerServiceProxy;
import edu.byu.cs.tweeter.model.service.MakeFollowServiceProxy;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;

public class MakeFollowPresenter
{
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }
    public MakeFollowPresenter(View view) {
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
    public MakeFollowResponse sendFollowRequest(MakeFollowRequest request) throws IOException, TweeterRemoteException
    {
        MakeFollowServiceProxy makeFollowService = getMakeFollowService();
        return makeFollowService.updateFollowServer(request);
    }

    /**
     * Returns an instance of {@link FollowerServiceProxy}. Allows mocking of the FollowingService class
     * for testing purposes. All usages of FollowingService should get their FollowingService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    MakeFollowServiceProxy getMakeFollowService() {
        return new MakeFollowServiceProxy();
    }
}
