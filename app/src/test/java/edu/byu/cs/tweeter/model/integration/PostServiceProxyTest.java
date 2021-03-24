package edu.byu.cs.tweeter.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.PostServiceProxy;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;

public class PostServiceProxyTest
{
    PostServiceProxy postServiceProxy;

    PostRequest passRequest;
    PostRequest failRequest;

    PostResponse passResponse;
    PostResponse failResponse;

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


        postServiceProxy = new PostServiceProxy();

        passRequest = new PostRequest(stat1);
        failRequest = new PostRequest(null);

        passResponse = new PostResponse(true);
        failResponse = new PostResponse(true);
    }
    //pass test
    @Test
    public void passTest() throws IOException, TweeterRemoteException
    {
        PostResponse test = postServiceProxy.updatePostServer(passRequest);

        Assertions.assertEquals(test.isSuccess(), passResponse.isSuccess());
    }
    //fail test
    @Test
    public void failTest() throws IOException, TweeterRemoteException
    {
        PostResponse test = postServiceProxy.updatePostServer(failRequest);

        Assertions.assertEquals(test.isSuccess(), failResponse.isSuccess());
    }
}
