package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutServiceTest
{
    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private LogoutResponse successResponse;
    private LogoutResponse failureResponse;

    private LogoutService logoutServiceSpy;

    /**
     * Create a LogoutService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        //User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

        User user1 = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        // Setup request objects to use in the tests
        validRequest = new LogoutRequest(user1);
        invalidRequest = new LogoutRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LogoutResponse(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.logout(validRequest)).thenReturn(successResponse);

        failureResponse = new LogoutResponse(false);
        Mockito.when(mockServerFacade.logout(invalidRequest)).thenReturn(failureResponse);

        // Create a LogoutService instance and wrap it with a spy that will use the mock service
        logoutServiceSpy = Mockito.spy(new LogoutService());
        Mockito.when(logoutServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link LogoutService#sendLogoutRequest(LogoutRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetLogout_validRequest_correctResponse() throws IOException {
        LogoutResponse response = logoutServiceSpy.sendLogoutRequest(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link LogoutService#sendLogoutRequest(LogoutRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetLogout_invalidRequest_returnsNoLogout() throws IOException {
        LogoutResponse response = logoutServiceSpy.sendLogoutRequest(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
