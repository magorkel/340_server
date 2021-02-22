package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.MakeUnfollowService;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;

public class MakeUnfollowPresenterTest {
    private MakeUnfollowRequest request;
    private MakeUnfollowResponse response;
    private MakeUnfollowService mockMakeUnfollowService;
    private MakeUnfollowPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
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

        request = new MakeUnfollowRequest(currentUser.getAlias(), resultUser1.getAlias());
        response = new MakeUnfollowResponse(true, "successfully unfollowed");

        // Create a mock MakeUnfollowService
        mockMakeUnfollowService = Mockito.mock(MakeUnfollowService.class);
        Mockito.when(mockMakeUnfollowService.sendUnfollowRequest(request)).thenReturn(response);

        // Wrap a MakeUnfollowPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new MakeUnfollowPresenter(new MakeUnfollowPresenter.View() {}));
        Mockito.when(presenter.getMakeUnfollowService()).thenReturn(mockMakeUnfollowService);
    }

    @Test
    public void testGetMakeUnfollow_returnsServiceResult() throws IOException {
        Mockito.when(mockMakeUnfollowService.sendUnfollowRequest(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.sendUnfollowRequest(request));
    }

    @Test
    public void testGetMakeUnfollow_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockMakeUnfollowService.sendUnfollowRequest(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.sendUnfollowRequest(request);
        });
    }
}
