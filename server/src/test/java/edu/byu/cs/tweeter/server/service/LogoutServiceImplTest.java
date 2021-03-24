package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class LogoutServiceImplTest
{
    private LogoutRequest request;
    private LogoutResponse expectedResponse;
    private AuthTokenDAO mockAuthTokenDAO;
    private LogoutServiceImpl logoutServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup a request object to use in the tests
        request = new LogoutRequest(resultUser1);

        // Setup a mock AuthTokenDAO that will return known responses
        expectedResponse = new LogoutResponse(true);
        mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);
        Mockito.when(mockAuthTokenDAO.deleteAuthToken(request.getUser().getAlias())).thenReturn(true);

        logoutServiceImplSpy = Mockito.spy(LogoutServiceImpl.class);
        Mockito.when(logoutServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);
    }

    /**
     * Verify that the {@link LogoutServiceImpl#getLogout(LogoutRequest)}
     * method returns the same result as the {@link AuthTokenDAO} class.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException
    {
        LogoutResponse response = logoutServiceImplSpy.getLogout(request);
        //LogoutResponse second = new LogoutResponse(true);
        //Assertions.assertEquals(second.isSuccess(), response.isSuccess());
        Assertions.assertEquals(expectedResponse.isSuccess(), response.isSuccess());
    }
}
