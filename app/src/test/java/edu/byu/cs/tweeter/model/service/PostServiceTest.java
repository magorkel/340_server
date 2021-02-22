package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;

public class PostServiceTest
{
    private PostRequest validRequest;
    private PostRequest invalidRequest;

    private PostResponse successResponse;
    private PostResponse failureResponse;

    private PostService postServiceSpy;

    /**
     * Create a PostService spy that uses a mock ServerFacade to return known responses to
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

        Status status1 = new Status("hello @James how are you? https://google.com", resultUser1, "Jan 1, 2021");
        Status status2 = new Status("@hi, says hello world", resultUser1, "Feb 2, 2019");
        Status status3 = new Status("@FirstNameLastName", resultUser2, "Today");

        // Setup request objects to use in the tests
        validRequest = new PostRequest(status1);
        invalidRequest = new PostRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostResponse(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.updatePostServer(validRequest)).thenReturn(successResponse);

        failureResponse = new PostResponse(false);
        Mockito.when(mockServerFacade.updatePostServer(invalidRequest)).thenReturn(failureResponse);

        // Create a PostService instance and wrap it with a spy that will use the mock service
        postServiceSpy = Mockito.spy(new PostService());
        Mockito.when(postServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link PostService#sendPostRequest(PostRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testPost_validRequest_correctResponse() throws IOException {
        PostResponse response = postServiceSpy.sendPostRequest(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link PostService#sendPostRequest(PostRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    /*@Test
    public void testPost_validRequest_loadsProfileImages() throws IOException {
        PostResponse response = postServiceSpy.sendPostRequest(validRequest);

        for(User user : response.sendPostRequest()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }*/

    /**
     * Verify that for failed requests the {@link PostService#sendPostRequest(PostRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testPost_invalidRequest_returnsNoPost() throws IOException {
        PostResponse response = postServiceSpy.sendPostRequest(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
