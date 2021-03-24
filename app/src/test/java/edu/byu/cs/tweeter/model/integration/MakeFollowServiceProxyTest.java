package edu.byu.cs.tweeter.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.MakeFollowServiceProxy;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;

public class MakeFollowServiceProxyTest
{
    MakeFollowServiceProxy makeFollowServiceProxy;

    MakeFollowRequest passRequest;
    MakeFollowRequest failRequest;

    MakeFollowResponse passResponse;
    MakeFollowResponse failResponse;

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



        makeFollowServiceProxy = new MakeFollowServiceProxy();

        passRequest = new MakeFollowRequest("james", "harley");
        failRequest = new MakeFollowRequest(null, null);

        passResponse = new MakeFollowResponse(true);
        failResponse = new MakeFollowResponse(true);
    }
    //pass test
    @Test
    public void passTest() throws IOException, TweeterRemoteException
    {
        MakeFollowResponse test = makeFollowServiceProxy.updateFollowServer(passRequest);

        Assertions.assertEquals(test.isSuccess(), passResponse.isSuccess());
    }
    //fail test
    @Test
    public void failTest() throws IOException, TweeterRemoteException
    {
        MakeFollowResponse test = makeFollowServiceProxy.updateFollowServer(failRequest);

        Assertions.assertEquals(test.isSuccess(), failResponse.isSuccess());
    }
}
