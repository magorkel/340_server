package edu.byu.cs.tweeter.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginServiceProxyTest
{
    LoginServiceProxy loginServiceProxy;

    LoginRequest passRequest;
    LoginRequest failRequest;

    LoginResponse passResponse;
    LoginResponse failResponse;

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



        loginServiceProxy = new LoginServiceProxy();

        passRequest = new LoginRequest("james", "tempPass");
        failRequest = new LoginRequest(null, null);

        passResponse = new LoginResponse(resultUser1,new AuthToken("hi"));
        failResponse = new LoginResponse("Password does not match");
    }
    //pass test
    @Test
    public void passTest() throws IOException, TweeterRemoteException
    {
        LoginResponse test = loginServiceProxy.login(passRequest);

        Assertions.assertEquals(test.isSuccess(), passResponse.isSuccess());
    }
    //fail test
    @Test
    public void failTest() throws IOException, TweeterRemoteException
    {
        LoginResponse test = loginServiceProxy.login(failRequest);

        Assertions.assertEquals(test.isSuccess(), failResponse.isSuccess());
    }
}
