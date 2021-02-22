package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;

public class MakeFollowServiceTest
{
    private MakeFollowRequest validRequest;
    private MakeFollowRequest invalidRequest;

    private MakeFollowResponse successResponse;
    private MakeFollowResponse failureResponse;

    private MakeFollowService makeFollowServiceSpy;

    /**
     * Create a MakeFollowService spy that uses a mock ServerFacade to return known responses to
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
        validRequest = new MakeFollowRequest(resultUser1.getAlias(), resultUser2.getAlias());
        invalidRequest = new MakeFollowRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new MakeFollowResponse(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.updateFollowServer(validRequest)).thenReturn(successResponse);

        failureResponse = new MakeFollowResponse(false);
        Mockito.when(mockServerFacade.updateFollowServer(invalidRequest)).thenReturn(failureResponse);

        // Create a MakeFollowService instance and wrap it with a spy that will use the mock service
        makeFollowServiceSpy = Mockito.spy(new MakeFollowService());
        Mockito.when(makeFollowServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link MakeFollowService#sendFollowRequest(MakeFollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException {
        MakeFollowResponse response = makeFollowServiceSpy.sendFollowRequest(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link MakeFollowService#sendFollowRequest(MakeFollowRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    /*@Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException {
        MakeFollowResponse response = makeFollowServiceSpy.sendFollowRequest(validRequest);

        response.
        for(User user : response.sendFollowRequest()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }*/

    /**
     * Verify that for failed requests the {@link MakeFollowService#sendFollowRequest(MakeFollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException {
        MakeFollowResponse response = makeFollowServiceSpy.sendFollowRequest(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
