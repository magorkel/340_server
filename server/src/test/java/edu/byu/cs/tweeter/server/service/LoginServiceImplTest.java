package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.PasswordDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class LoginServiceImplTest
{
    private LoginRequest request;
    private LoginResponse expectedResponse;
    private UserDAO mockUserDAO;
    private PasswordDAO mockPasswordDAO;
    private LoginServiceImpl loginServiceImplSpy;

    @BeforeEach
    public void setup() {
        User user1 = new User("Allen", "Anderson", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup a request object to use in the tests
        request = new LoginRequest(currentUser.getAlias(), "tempPass");

        // Setup a mock UserDAO that will return known responses
        expectedResponse = new LoginResponse(user1, new AuthToken());
        mockPasswordDAO = Mockito.mock(PasswordDAO.class);
        mockUserDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockUserDAO.getUser(request.getUsername())).thenReturn(user1);
        Mockito.when(mockPasswordDAO.findPassword(request.getUsername())).thenReturn(request.getUsername());

        loginServiceImplSpy = Mockito.spy(LoginServiceImpl.class);
        Mockito.when(loginServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);
    }

    /**
     * Verify that the {@link LoginServiceImpl#login(LoginRequest)}
     * method returns the same result as the {@link UserDAO} class.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException
    {
        LoginResponse response = loginServiceImplSpy.login(request);
        Assertions.assertEquals(expectedResponse.isSuccess(), response.isSuccess());
    }
}
