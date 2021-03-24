package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class PostServiceImplTest
{
    private PostRequest request;
    private PostResponse expectedResponse;
    private StatusDAO mockPostDAO;
    private PostServiceImpl makePostServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);

        Status stat1 = new Status("@AllenAnderson @hi content1 https://google.com", user1, "Wednesday, September 22, 2021");
        Status stat2 = new Status("hello content2", user1, "Thursday, December 4, 2021");
        Status stat3 = new Status("hello content3", user1, "Wednesday, June 22, 2021");
        Status stat4 = new Status("hello content4", user1, "Thursday, January 4, 2021");

        // Setup a request object to use in the tests
        request = new PostRequest(stat1);

        // Setup a mock PostDAO that will return known responses
        expectedResponse = new PostResponse(true);
        mockPostDAO = Mockito.mock(StatusDAO.class);
        Mockito.when(mockPostDAO.updatePostServer(request)).thenReturn(expectedResponse);

        makePostServiceImplSpy = Mockito.spy(PostServiceImpl.class);
        Mockito.when(makePostServiceImplSpy.getStatusDAO()).thenReturn(mockPostDAO);
    }

    /**
     * Verify that the {@link PostServiceImpl#updatePostServer(PostRequest)}
     * method returns the same result as the {@link StatusDAO} class.
     */
    @Test
    public void testGetPostees_validRequest_correctResponse() throws IOException, TweeterRemoteException
    {
        PostResponse response = makePostServiceImplSpy.updatePostServer(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
