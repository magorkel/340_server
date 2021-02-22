package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterServiceTest
{
    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;

    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;

    private RegisterService registerServiceSpy;

    /**
     * Create a RegisterService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup request objects to use in the tests
        validRequest = new RegisterRequest(resultUser1.getName(), "tempPass", resultUser1.getFirstName(), resultUser1.getLastName(), null);
        invalidRequest = new RegisterRequest(null, null, null, null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new RegisterResponse(resultUser1, new AuthToken());
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.register(validRequest)).thenReturn(successResponse);

        failureResponse = new RegisterResponse("An exception occurred");
        Mockito.when(mockServerFacade.register(invalidRequest)).thenReturn(failureResponse);

        // Create a RegisterService instance and wrap it with a spy that will use the mock service
        registerServiceSpy = Mockito.spy(new RegisterService());
        Mockito.when(registerServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link RegisterService#register(RegisterRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testRegister_validRequest_correctResponse() throws IOException {
        RegisterResponse response = registerServiceSpy.register(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link RegisterService#register(RegisterRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    /*@Test
    public void testRegister_validRequest_loadsProfileImages() throws IOException {
        RegisterResponse response = registerServiceSpy.register(validRequest);
        Assertions.assertNotNull(response.getUser().getImageBytes());
    }*/

    /**
     * Verify that for failed requests the {@link RegisterService#register(RegisterRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testRegister_invalidRequest_returnsNoRegister() throws IOException {
        RegisterResponse response = registerServiceSpy.register(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
