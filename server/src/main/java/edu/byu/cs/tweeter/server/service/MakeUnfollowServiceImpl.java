package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.MakeUnfollowService;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;

import edu.byu.cs.tweeter.server.dao.FollowingDAO;

public class MakeUnfollowServiceImpl implements MakeUnfollowService {
    @Override
    public MakeUnfollowResponse updateUnfollowServer(MakeUnfollowRequest request) {
        return getFollowingDAO().updateUnfollowServer(request);
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