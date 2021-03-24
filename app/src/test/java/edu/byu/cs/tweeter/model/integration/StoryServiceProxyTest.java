package edu.byu.cs.tweeter.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.StoryServiceProxy;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryServiceProxyTest
{
    StoryServiceProxy storyServiceProxy;

    StoryRequest passRequest;
    StoryRequest failRequest;

    StoryResponse passResponse;
    StoryResponse failResponse;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException
    {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);

        Status stat1 = new Status("@AllenAnderson @hi content1 https://google.com", user1, "Wednesday, September 22, 2021");
        Status stat2 = new Status("hello content2", user1, "Thursday, December 4, 2021");
        Status stat3 = new Status("hello content3", user1, "Wednesday, June 22, 2021");
        Status stat4 = new Status("hello content4", user1, "Thursday, January 4, 2021");

        ArrayList<Status> statuses = new ArrayList<Status>();
        statuses.add(stat1);
        statuses.add(stat2);
        statuses.add(stat3);
        statuses.add(stat4);
        statuses.add(stat1);
        statuses.add(stat2);
        statuses.add(stat3);
        statuses.add(stat4);
        statuses.add(stat1);
        statuses.add(stat2);

        storyServiceProxy = new StoryServiceProxy();

        passRequest = new StoryRequest("james", 10, stat1);
        failRequest = new StoryRequest(null, 0, null);

        passResponse = new StoryResponse(statuses,true);
        failResponse = new StoryResponse(statuses, false);
    }
    //pass test
    @Test
    public void passTest() throws IOException, TweeterRemoteException
    {
        StoryResponse test = storyServiceProxy.getStory(passRequest);

        Assertions.assertEquals(test.isSuccess(), passResponse.isSuccess());
    }
    //fail test
    @Test
    public void failTest() throws IOException, TweeterRemoteException
    {
        StoryResponse test = storyServiceProxy.getStory(failRequest);

        Assertions.assertEquals(test.isSuccess(), failResponse.isSuccess());
    }
}
