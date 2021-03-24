package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginPresenterTest {
    private LoginRequest request;
    private LoginResponse response;
    private LoginServiceProxy mockLoginService;
    private LoginPresenter presenter;

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

        User testUser = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        request = new LoginRequest(testUser.getAlias(), "tempPass");
        response = new LoginResponse(testUser, new AuthToken());

        // Create a mock LoginService
        mockLoginService = Mockito.mock(LoginServiceProxy.class);
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        // Wrap a LoginPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginService);
    }

    @Test
    public void testGetLogin_returnsServiceResult() throws IOException, TweeterRemoteException
    {
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        LoginResponse tempR = response;
        LoginResponse temp = presenter.login(request);
        //Here, we need to compare users, as the way that the facade works is that it creates a random new auth token
        //on each login. So it is impossible for it to match on both requests (it will be different each time).
        Assertions.assertEquals(response, presenter.login(request));
    }

    @Test
    public void testGetLogin_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException
    {
        Mockito.when(mockLoginService.login(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.login(request);
        });
    }
}
