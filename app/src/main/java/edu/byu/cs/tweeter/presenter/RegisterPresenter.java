package edu.byu.cs.tweeter.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.RegisterServiceProxy;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterPresenter
{
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public RegisterPresenter(RegisterPresenter.View view) {
        this.view = view;
    }

    /**
     * Makes a login request.
     *
     * @param registerRequest the request.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public RegisterResponse register(RegisterRequest registerRequest) throws IOException, TweeterRemoteException
    {
        RegisterServiceProxy registerService = getRegisterService();
        return registerService.getRegister(registerRequest);
    }

    //This is for Mockito Testing
    RegisterServiceProxy getRegisterService() {
        return new RegisterServiceProxy();
    }
}
