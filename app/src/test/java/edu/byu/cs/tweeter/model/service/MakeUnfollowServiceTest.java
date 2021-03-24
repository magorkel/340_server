package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;

public class MakeUnfollowServiceTest
{
    private MakeUnfollowRequest validRequest;
    private MakeUnfollowRequest invalidRequest;

    private MakeUnfollowResponse successResponse;
    private MakeUnfollowResponse failureResponse;

    private MakeUnfollowServiceProxy makeUnfollowServiceSpy;

    /**
     * Create a MakeUnfollowService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
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

        // Setup request objects to use in the tests
        validRequest = new MakeUnfollowRequest(resultUser1.getAlias(), resultUser2.getAlias());
        invalidRequest = new MakeUnfollowRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new MakeUnfollowResponse(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.updateUnfollowServer(validRequest)).thenReturn(successResponse);

        failureResponse = new MakeUnfollowResponse(false);
        Mockito.when(mockServerFacade.updateUnfollowServer(invalidRequest)).thenReturn(failureResponse);

        // Create a MakeUnfollowService instance and wrap it with a spy that will use the mock service
        makeUnfollowServiceSpy = Mockito.spy(new MakeUnfollowServiceProxy());
        Mockito.when(makeUnfollowServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link MakeUnfollowServiceProxy#updateUnfollowServer(MakeUnfollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUnfollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException
    {
        MakeUnfollowResponse response = makeUnfollowServiceSpy.updateUnfollowServer(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link MakeUnfollowServiceProxy#updateUnfollowServer(MakeUnfollowRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    /*@Test
    public void testGetUnfollowees_validRequest_loadsProfileImages() throws IOException {
        MakeUnfollowResponse response = makeUnfollowServiceSpy.sendUnfollowRequest(validRequest);

        response.
        for(User user : response.sendUnfollowRequest()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }*/

    /**
     * Verify that for failed requests the {@link MakeUnfollowServiceProxy#updateUnfollowServer(MakeUnfollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUnfollow_invalidRequest_returnsUnfollow() throws IOException, TweeterRemoteException
    {
        MakeUnfollowResponse response = makeUnfollowServiceSpy.updateUnfollowServer(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
