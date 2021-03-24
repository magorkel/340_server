package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class MakeFollowServiceImplTest
{
    private MakeFollowRequest request;
    private MakeFollowResponse expectedResponse;
    private FollowingDAO mockMakeFollowDAO;
    private MakeFollowServiceImpl makeFollowServiceImplSpy;

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
        request = new MakeFollowRequest(currentUser.getAlias(), resultUser1.getAlias());

        // Setup a mock MakeFollowDAO that will return known responses
        expectedResponse = new MakeFollowResponse(true);
        mockMakeFollowDAO = Mockito.mock(FollowingDAO.class);
        Mockito.when(mockMakeFollowDAO.updateFollowServer(request)).thenReturn(expectedResponse);

        makeFollowServiceImplSpy = Mockito.spy(MakeFollowServiceImpl.class);
        Mockito.when(makeFollowServiceImplSpy.getFollowingDAO()).thenReturn(mockMakeFollowDAO);
    }

    /**
     * Verify that the {@link MakeFollowServiceImpl#updateFollowServer(MakeFollowRequest)}
     * method returns the same result as the {@link FollowingDAO} class.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException
    {
        MakeFollowResponse response = makeFollowServiceImplSpy.updateFollowServer(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
