package edu.byu.cs.tweeter.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowingServiceProxy;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

public class FollowingServiceProxyTest
{
    FollowingServiceProxy followingServiceProxy;

    FollowingRequest passRequest;
    FollowingRequest failRequest;

    FollowingResponse passResponse;
    FollowingResponse failResponse;

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



        followingServiceProxy = new FollowingServiceProxy();

        passRequest = new FollowingRequest("james", 10, "Allen");
        failRequest = new FollowingRequest(null, 0, null);

        passResponse = new FollowingResponse(Arrays.asList(resultUser1,resultUser2,resultUser3),true);
        failResponse = new FollowingResponse(null);
    }
    //pass test
    @Test
    public void passTest() throws IOException, TweeterRemoteException
    {
        FollowingResponse test = followingServiceProxy.getFollowees(passRequest);

        Assertions.assertEquals(test.getHasMorePages(), passResponse.getHasMorePages());
    }
    //fail test
    @Test
    public void failTest() throws IOException, TweeterRemoteException
    {
        FollowingResponse test = followingServiceProxy.getFollowees(failRequest);

        Assertions.assertEquals(test.getHasMorePages(), failResponse.getHasMorePages());
    }
}
