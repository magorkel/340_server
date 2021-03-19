package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.PasswordDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) {

        //1. get a list of users from UserDAO - these are handled inside of userDAO
        //2. find user from list - handled inside of userDAO
        User user = getUserDAO().getUser(request.getUsername());
        //3. if we don't find - return error
        //4. match password with PasswordDAO
        if (getPasswordDAO().findPassword(request.getUsername()).equals(request.getPassword()))
        {
            AuthToken authToken = getAuthTokenDAO().createAuthToken(request.getUsername());
            return new LoginResponse(user, authToken);
        }
        else
        {
            return new LoginResponse("Password does not match");
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
