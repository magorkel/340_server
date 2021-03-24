package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatusDAO {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private Status stat1 = new Status("@AllenAnderson @hi content1 https://google.com", user1, "Wednesday, September 22, 2021");
    //private Status stat1 = new Status("@hello @sup content1 https://google.com @hi ", user1, "Wednesday, September 22, 2021");
    private Status stat2 = new Status("hello content2", user1, "Thursday, December 4, 2021");
    private Status stat3 = new Status("hello content3", user1, "Wednesday, June 22, 2021");
    private Status stat4 = new Status("hello content4", user1, "Thursday, January 4, 2021");

    public PostResponse updatePostServer(PostRequest request)
    {
        //add to feed and story
        return new PostResponse(true, "successfully posted");
    }

    public FeedResponse getFeed(FeedRequest request) {
        // Used in place of assert statements because Android does not support them
        /*
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }
            if(request.getUsername() == null) {
                throw new AssertionError();
            }
        }*/
        /*if (request.getUsername() != null)
        {
            if (request.getUsername().equals("fail now"))
            {
                return new FeedResponse("Fail");
            }
        }*/
        List<Status> allStatuses = getDummyStatuses();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());
        boolean hasMorePages = false;
        if(request.getLimit() > 0) {
            int statusIndex = getStatusStartingIndex(request.getLastStatus(), allStatuses);
            for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusIndex));
            }
            hasMorePages = statusIndex < allStatuses.size();
        }
        return new FeedResponse(responseStatuses, hasMorePages);
    }

    public StoryResponse getStory(StoryRequest request) {
        // Used in place of assert statements because Android does not support them
        /*if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }
            if(request.getUsername() == null) {
                throw new AssertionError();
            }
        }*/
        List<Status> allStatuses = getDummyStatuses();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());
        boolean hasMorePages = false;
        if(request.getLimit() > 0) {
            int statusIndex = getStatusStartingIndex(request.getLastStatus(), allStatuses);
            for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusIndex));
            }
            hasMorePages = statusIndex < allStatuses.size();
        }
        return new StoryResponse(responseStatuses, hasMorePages);
    }

    private int getStatusStartingIndex(Status lastStatus, List<Status> allStatuses) {//getstatusstartingindex     string alias is a status
        int followersIndex = 0;
        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i).getUserAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                    break;
                }
            }
        }
        return followersIndex;
    }

    List<Status> getDummyStatuses() {
        return Arrays.asList(stat1, stat2, stat3, stat4, stat1, stat2, stat3, stat4, stat1, stat2);
    }
}