package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class MakeUnfollowServiceImplTest
{
    private MakeUnfollowRequest request;
    private MakeUnfollowResponse expectedResponse;
    private FollowingDAO mockMakeUnfollowDAO;
    private MakeUnfollowServiceImpl makeUnfollowServiceImplSpy;

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
        request = new MakeUnfollowRequest(currentUser.getAlias(), resultUser1.getAlias());

        // Setup a mock MakeUnfollowDAO that will return known responses
        expectedResponse = new MakeUnfollowResponse(true);
        mockMakeUnfollowDAO = Mockito.mock(FollowingDAO.class);
        Mockito.when(mockMakeUnfollowDAO.updateUnfollowServer(request)).thenReturn(expectedResponse);

        makeUnfollowServiceImplSpy = Mockito.spy(MakeUnfollowServiceImpl.class);
        Mockito.when(makeUnfollowServiceImplSpy.getFollowingDAO()).thenReturn(mockMakeUnfollowDAO);
    }

    /**
     * Verify that the {@link MakeUnfollowServiceImpl#updateUnfollowServer(MakeUnfollowRequest)}
     * method returns the same result as the {@link FollowingDAO} class.
     */
    @Test
    public void testGetUnfollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException
    {
        MakeUnfollowResponse response = makeUnfollowServiceImplSpy.updateUnfollowServer(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
