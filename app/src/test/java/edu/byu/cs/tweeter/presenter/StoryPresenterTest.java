package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.StoryServiceProxy;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryPresenterTest {
    private StoryRequest request;
    private StoryResponse response;
    private StoryServiceProxy mockStoryService;
    private StoryPresenter presenter;

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

        Status status1 = new Status("hello @James how are you? https://google.com", resultUser1, "Jan 1, 2021");
        Status status2 = new Status("@hi, says hello world", resultUser1, "Feb 2, 2019");
        Status status3 = new Status("@FirstNameLastName", resultUser2, "Today");

        request = new StoryRequest(currentUser.getAlias(), 3, null);
        response = new StoryResponse(Arrays.asList(status1, status2, status3), false);

        // Create a mock StoryService
        mockStoryService = Mockito.mock(StoryServiceProxy.class);
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);

        // Wrap a StoryPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new StoryPresenter(new StoryPresenter.View() {}));
        Mockito.when(presenter.getStoryService()).thenReturn(mockStoryService);
    }

    @Test
    public void testGetStory_returnsServiceResult() throws IOException, TweeterRemoteException
    {
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getStory(request));
    }

    @Test
    public void testGetStory_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException
    {
        Mockito.when(mockStoryService.getStory(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getStory(request);
        });
    }
}
