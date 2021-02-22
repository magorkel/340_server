package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutPresenterTest {
    private LogoutRequest request;
    private LogoutResponse response;
    private LogoutService mockLogoutService;
    private LogoutPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        User testUser = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        request = new LogoutRequest(testUser);
        response = new LogoutResponse(true);

        // Create a mock LogoutService
        mockLogoutService = Mockito.mock(LogoutService.class);
        Mockito.when(mockLogoutService.sendLogoutRequest(request)).thenReturn(response);

        // Wrap a LogoutPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new LogoutPresenter(new LogoutPresenter.View() {}));
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
    }

    @Test
    public void testGetLogout_returnsServiceResult() throws IOException {
        Mockito.when(mockLogoutService.sendLogoutRequest(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        LogoutResponse tempR = response;
        LogoutResponse temp = presenter.sendLogoutRequest(request);
        //Here, we need to compare users, as the way that the facade works is that it creates a random new auth token
        //on each login. So it is impossible for it to match on both requests (it will be different each time).
        Assertions.assertEquals(response, presenter.sendLogoutRequest(request));
    }

    @Test
    public void testGetLogout_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockLogoutService.sendLogoutRequest(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.sendLogoutRequest(request);
        });
    }
}
