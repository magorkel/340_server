package edu.byu.cs.tweeter.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FeedServiceProxy;
import edu.byu.cs.tweeter.model.service.FollowerServiceProxy;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

public class FollowerServiceProxyTest
{
    FollowerServiceProxy followerServiceProxy;

    FollowerRequest passRequest;
    FollowerRequest failRequest;

    FollowerResponse passResponse;
    FollowerResponse failResponse;

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



        followerServiceProxy = new FollowerServiceProxy();

        passRequest = new FollowerRequest("james", 10, "Allen");
        failRequest = new FollowerRequest(null, 0, null);

        passResponse = new FollowerResponse(Arrays.asList(resultUser1,resultUser2,resultUser3),true);
        failResponse = new FollowerResponse(null);
    }
    //pass test
    @Test
    public void passTest() throws IOException, TweeterRemoteException
    {
        FollowerResponse test = followerServiceProxy.getFollowers(passRequest);

        Assertions.assertEquals(test.getHasMorePages(), passResponse.getHasMorePages());
    }
    //fail test
    @Test
    public void failTest() throws IOException, TweeterRemoteException
    {
        FollowerResponse test = followerServiceProxy.getFollowers(failRequest);

        Assertions.assertEquals(test.getHasMorePages(), failResponse.getHasMorePages());
    }
}
