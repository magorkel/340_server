package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.MakeFollowService;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

public class MakeFollowServiceImpl implements MakeFollowService {
    @Override
    public MakeFollowResponse updateFollowServer(MakeFollowRequest request) {
        return getFollowingDAO().updateFollowServer(request);
    }
    /**
     * Returns an instance of {@link FollowingDAO}. Allows mocking of the FollowingDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowingDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowingDAO getFollowingDAO() {
        return new FollowingDAO();
    }
}