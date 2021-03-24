package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.PasswordDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

import java.io.IOException;

public class RegisterServiceImpl implements RegisterService
{
    @Override
    public RegisterResponse getRegister(RegisterRequest request)
    {
        //register user inside dao
        //register password inside dao
        //get authtoken same way for login

        User user = new User(request.getFirstName(),request.getLastName(),"@" + request.getFirstName()+request.getLastName(), request.getImage());
        boolean userCreated = getUserDAO().registerUser(user, request.getUsername());

        boolean passwordCreated = getPasswordDAO().createPassword(request.getUsername(), request.getPassword());

        AuthToken authToken = getAuthTokenDAO().createAuthToken(request.getUsername());


        if (userCreated) {
            if (passwordCreated) {
                //AuthToken authToken = getAuthTokenDAO().createAuthToken(request.getUsername());
                return new RegisterResponse(user, authToken);
            }
            else
            {
                return new RegisterResponse("Error making password");
            }
        }
        else
        {
            return new RegisterResponse("User already exists");
        }
    }

    UserDAO getUserDAO()
    {
        return new UserDAO();
    }

    AuthTokenDAO getAuthTokenDAO()
    {
        return new AuthTokenDAO();
    }

    PasswordDAO getPasswordDAO()
    {
        return new PasswordDAO();
    }
}
