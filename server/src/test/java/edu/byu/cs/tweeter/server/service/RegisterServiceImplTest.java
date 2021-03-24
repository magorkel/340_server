package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.PasswordDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class RegisterServiceImplTest
{
    private RegisterRequest request;
    private RegisterResponse expectedResponse;
    private UserDAO mockUserDAO;
    private PasswordDAO mockPasswordDAO;
    private AuthTokenDAO mockAuthTokenDAO;
    private RegisterServiceImpl registerServiceImplSpy;

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
        request = new RegisterRequest("username", "password", resultUser1.getFirstName(), resultUser1.getLastName(), null);

        // Setup a mock UserDAO that will return known responses
        expectedResponse = new RegisterResponse("user1, new AuthToken()");
        mockPasswordDAO = Mockito.mock(PasswordDAO.class);
        mockUserDAO = Mockito.mock(UserDAO.class);
        mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);
        Mockito.when(mockUserDAO.getUser(request.getUsername())).thenReturn(user1);
        Mockito.when(mockPasswordDAO.findPassword(request.getUsername())).thenReturn(request.getUsername());
        AuthToken newAuthToken = new AuthToken();
        Mockito.when(mockAuthTokenDAO.createAuthToken(request.getUsername())).thenReturn(newAuthToken);

        registerServiceImplSpy = Mockito.spy(RegisterServiceImpl.class);
        Mockito.when(registerServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);
    }

    /**
     * Verify that the {@link RegisterServiceImpl#getRegister(RegisterRequest)}
     * method returns the same result as the {@link UserDAO} class.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException
    {
        RegisterResponse response = registerServiceImplSpy.getRegister(request);
        Assertions.assertEquals(expectedResponse.isSuccess(), response.isSuccess());
    }
}
