package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) {

        //1. get a list of users from UserDAO
        //2. find user from list
        //3. if we don't find - return error
        //4. match password with PasswordDAO
        //5. create an authtoken in this class and send it to AuthTokenDAO (where it would then register it and put it in the database)
        if (request.getPassword().equals("tempPass")) {
            User user = new User("Test", "User",
                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            return new LoginResponse(user, new AuthToken());
        }
        else {
            //Failed to find password
            return new LoginResponse("Could not find Password =(");
        }

        /*// TODO: Generates dummy data. Replace with a real implementation.
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new LoginResponse(user, new AuthToken());*/
    }
}
