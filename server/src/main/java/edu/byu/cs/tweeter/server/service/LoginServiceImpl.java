package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.PasswordDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) {

        //1. get a list of users from UserDAO - these are handled inside of userDAO
        //2. find user from list - handled inside of userDAO
        //String userAlias = getPasswordDAO().getPassword(request.getPassword());
        //3. if we don't find - return error
        //4. match password with PasswordDAO
        List<String> userInfo = getPasswordDAO().findPassword(request.getUsername());
        if (!userInfo.isEmpty())
        {
            String tempPass = getSecurePassword(request.getPassword(), userInfo.get(2));
            if (userInfo.get(0).equals(tempPass))
            {
                //make authtoken
                AuthToken authToken = getAuthTokenDAO().createAuthToken(request.getUsername());
                User user = getUserDAO().getUser(userInfo.get(1));
                return new LoginResponse(user, authToken);
            }
            else
            {
                return new LoginResponse("Password does not match");
            }
        }
        else
        {
            return new LoginResponse("User does not exist");
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

    private static String getSecurePassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "FAILED TO HASH PASSWORD";
    }
}
