package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.server.dao.StatusDAO;

public class FeedServiceImpl implements FeedService { //Need an SDK to fix this error.
    @Override
    public FeedResponse getFeed(FeedRequest request) {
        return getStatusDAO().getFeed(request);
    }
    /**
     * Returns an instance of {@link FollowingDAO}. Allows mocking of the FollowingDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowingDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    StatusDAO getStatusDAO() {
        return new StatusDAO();
    }
}