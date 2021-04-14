package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

import java.io.*;
import java.net.URL;
import java.util.Base64;

public class RegisterServiceImpl implements RegisterService
{
    @Override
    public RegisterResponse getRegister(RegisterRequest request) {
        //register user inside dao
        //register password inside dao
        //get authtoken same way for login

        //s3 bucket?
        System.out.println("got register request");
        AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
                //.standard()
                //.withRegion("us-west-2")
                //.build();

        System.out.println("after trying initialize s3 bucket");

        ByteArrayInputStream bs = new ByteArrayInputStream(request.getImage());

        System.out.println("bs " + bs);
        //String newBitmap = Base64.getEncoder().encodeToString(request.getImage());


        PutObjectRequest objectRequest = new PutObjectRequest("bitmaptostringurl", request.getUsername()+"Image.png", bs, new ObjectMetadata());
        //PutObjectRequest objectRequest = new PutObjectRequest("bitmaptostringurl", request.getUsername()+"Image.png", new File(request.getUsername()+"Image.png"));
        /*PutObjectRequest objectRequest = new PutObjectRequest("bitmaptostringurl"
                , request.getUsername() + "Image.png"
                , bs
                , new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead);*/

        s3.putObject(objectRequest.withCannedAcl(CannedAccessControlList.PublicRead));

        System.out.println("request sent to s3");
        String url = s3.getUrl("bitmaptostringurl", request.getUsername()+"Image.png").toString();

        System.out.println("url " + url);


        User user = new User(request.getFirstName(),request.getLastName(),"@" + request.getFirstName()+request.getLastName(), url.toString());
        boolean userCreated = getUserDAO().registerUser(user, request.getUsername());

        if (userCreated) {
            boolean passwordCreated = getPasswordDAO().createPassword(request.getUsername(), request.getPassword(), "@" + request.getFirstName() + request.getLastName());
            if (passwordCreated) {
                AuthToken authToken = getAuthTokenDAO().createAuthToken(request.getUsername());
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
