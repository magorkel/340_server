package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.RegisterServiceProxy;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterPresenterTest {
    private RegisterRequest request;
    private RegisterResponse response;
    private RegisterServiceProxy mockRegisterService;
    private RegisterPresenter presenter;

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
        //Create a byte array from the given URL
        //Bitmap bm1 = Bitmap.createBitmap(100, 200, Bitmap.Config.ARGB_8888);

        request = new RegisterRequest(testUser.getName(), "Password", testUser.getFirstName(), testUser.getLastName(), "");
        response = new RegisterResponse(testUser, new AuthToken());

        // Create a mock RegisterService
        mockRegisterService = Mockito.mock(RegisterServiceProxy.class);
        Mockito.when(mockRegisterService.getRegister(request)).thenReturn(response);

        // Wrap a RegisterPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new RegisterPresenter(new RegisterPresenter.View() {}));
        Mockito.when(presenter.getRegisterService()).thenReturn(mockRegisterService);
    }

    @Test
    public void testGetRegister_returnsServiceResult() throws IOException, TweeterRemoteException
    {
        Mockito.when(mockRegisterService.getRegister(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        RegisterResponse tempR = response;
        RegisterResponse temp = presenter.register(request);
        //Here, we need to compare users, as the way that the facade works is that it creates a random new auth token
        //on each login. So it is impossible for it to match on both requests (it will be different each time).
        Assertions.assertEquals(response, presenter.register(request));
    }

    @Test
    public void testGetRegister_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException
    {
        Mockito.when(mockRegisterService.getRegister(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.register(request);
        });
    }
}
