package edu.byu.cs.tweeter.model.net;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    // This is the hard coded followee data returned by the 'getFollowees()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private ClientCommunicator clientCommunicator = new ClientCommunicator("https://fqqrokppv3.execute-api.us-west-2.amazonaws.com/dev");

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);
    private Status stat1 = new Status("@AllenAnderson @hi content1 https://google.com", user1, "Wednesday, September 22, 2021");
    //private Status stat1 = new Status("@hello @sup content1 https://google.com @hi ", user1, "Wednesday, September 22, 2021");
    private Status stat2 = new Status("hello content2", user1, "Thursday, December 4, 2021");
    private Status stat3 = new Status("hello content3", user1, "Wednesday, June 22, 2021");
    private Status stat4 = new Status("hello content4", user1, "Thursday, January 4, 2021");

    private ArrayList<User> userList = new ArrayList<>();

    //private Story story = new Story(stat1, stat2, "@billyjoe");//make a list of story objects we can iterate through
    //private User user21 = new User("Billy", "BobJoe", "@billybobjoe", MALE_IMAGE_URL);


    public UserResponse findUser(UserRequest request) throws IOException, TweeterRemoteException
    {
        //check through db of users
        /*userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);

       // boolean found = false;
        User retUser = null;

        for (int i = 0; i < userList.size(); i++) {
            if (request.getUserAlias().equals(userList.get(i).getAlias())) {
                //found = true;
                retUser = userList.get(i);
                break;
            }
        }

        //if found, return
        if (retUser != null) {
            return new UserResponse(retUser);
        }
        else {
            return new UserResponse("Could not find User");
        }
        //else return error*/
        String urlPath = "/getuser";
        UserResponse response = clientCommunicator.doPost(urlPath, request, null, UserResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException
    {
        //Check and verify that password is correct
        /*if (request.getPassword().equals("tempPass")) {
            User user = new User("Test", "User",
                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            return new LoginResponse(user, new AuthToken());
        }
        else {
            //Failed to find password
            return new LoginResponse("Could not find Password =(");
        }*/
        String urlPath = "/login";
        LoginResponse response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public RegisterResponse register(RegisterRequest request) {
        //This one will check all usernames and make sure yours is not taken before returning.
        ArrayList taken = new ArrayList<String> ();
        taken.add("freddyJ");
        taken.add("daphneB");
        taken.add("velmaD");
        taken.add("scoobyD");
        boolean unique = true;
        for (int i = 0; i < taken.size(); i++) {
            if (request.getUsername().equals(taken.get(i))) {
                unique = false;
            }
        }
        if (unique) {
            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bmp = request.getImageBytes();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            ByteArrayInputStream bs = new ByteArrayInputStream(byteArray);

            String newBitmap = Base64.getEncoder().encodeToString(byteArray);
            bmp.recycle();*/

            //avatar.recycle();

            //send to s3, get url back, send url instead of image
            // Create AmazonS3 object for doing S3 operations

            //ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //bmp.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            //byte[] bitmapdata = bos.toByteArray();

            /*AmazonS3Client s3 = (AmazonS3Client) AmazonS3ClientBuilder.defaultClient();


            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withRegion("us-west-2")
                    .build();
            // Write code to do the following:
            // 1. get name of file to be copied from the command line
            // 2. get name of S3 bucket from the command line
            // 3. upload file to the specified S3 bucket using the file name as the S3 key
            PutObjectRequest objectRequest = new PutObjectRequest("bitmaptostringurl", request.getUsername() + "Image.png", bs, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead);
            ;
            s3.putObject(objectRequest);
            //PutObjectRequest(java.lang.String bucketName,
            //                        java.lang.String key,
            //                        java.io.InputStream input,
            //                        ObjectMetadata metadata)

            //s3.putObject("bitmaptostringurl", bs, "Upload bitmap");

            String url = s3.getResourceUrl("bitmaptostringurl", request.getUsername()+"Image.png");*/
            User user = new User(request.getFirstName(), request.getLastName(), "@" + request.getFirstName() + request.getLastName(),
                    request.getImage()/*newBitmap*/);//decode back into a bitearray and then do all that above
            return new RegisterResponse(user, new AuthToken());
        }
        else {
            return new RegisterResponse("Error, Username taken");
        }

    }

    public MakeFollowResponse updateFollowServer(MakeFollowRequest request) throws IOException, TweeterRemoteException
    {
        //pull out two users and update their lists
        //return new MakeFollowResponse(true,"successfully followed");
        String urlPath = "/getmakefollow";
        MakeFollowResponse response = clientCommunicator.doPost(urlPath, request, null, MakeFollowResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public MakeUnfollowResponse updateUnfollowServer(MakeUnfollowRequest request) throws IOException, TweeterRemoteException
    {
        //return new MakeUnfollowResponse(true,"successfully unfollowed");
        String urlPath = "/getmakeunfollow";
        MakeUnfollowResponse response = clientCommunicator.doPost(urlPath, request, null, MakeUnfollowResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public PostResponse updatePostServer(PostRequest request) throws IOException, TweeterRemoteException
    {
        //add to feed and story
        //return new PostResponse(true, "successfully posted");
        String urlPath = "/getpost";
        PostResponse response = clientCommunicator.doPost(urlPath, request, null, PostResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public LogoutResponse logout(LogoutRequest request)
    {
        //Check and verify that password is correct
        //verifies that user is in database
        //grab authtoken
        //nullify authtoken

        return new LogoutResponse(true, "successfully logged out");
    }


    //Followee starts here
    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowingResponse getFollowees(FollowingRequest request/*, String urlPath*/) throws IOException, TweeterRemoteException {

        // Used in place of assert statements because Android does not support them
        /*if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowerAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followeesIndex = getFolloweesStartingIndex(request.getLastFolloweeAlias(), allFollowees);

            for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowees.add(allFollowees.get(followeesIndex));
            }

            hasMorePages = followeesIndex < allFollowees.size();
        }

        return new FollowingResponse(responseFollowees, hasMorePages);*/
        String urlPath = "/getfollowing";
        FollowingResponse response = clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFolloweeAlias.equals(allFollowees.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    //Follower starts here----------------------------------------------------------------------------------------------------------------------------
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException, TweeterRemoteException
    {

        /*// Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowerAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followersIndex = getFollowersStartingIndex(request.getLastFollowerAlias(), allFollowers);

            for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowerResponse(responseFollowers, hasMorePages);*/
        String urlPath = "/getfollower";
        FollowerResponse response = clientCommunicator.doPost(urlPath, request, null, FollowerResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    private int getFollowersStartingIndex(String lastFollowerAlias, List<User> allFollowers) {//getstatusstartingindex     string alias is a status

        int followersIndex = 0;

        if(lastFollowerAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollowerAlias.equals(allFollowers.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                    break;
                }
            }
        }

        return followersIndex;
    }

    List<User> getDummyFollowers() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    //Story starts here---------------------------------------------------------------------------------------------------------------------
    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException
    {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUsername() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getDummyStatuses();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusIndex = getStatusStartingIndex(request.getLastStatus(), allStatuses);

            for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusIndex));
            }

            hasMorePages = statusIndex < allStatuses.size();
        }

        return new StoryResponse(responseStatuses, hasMorePages);
        /*String urlPath = "/getstory";
        StoryResponse response = clientCommunicator.doPost(urlPath, request, null, StoryResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }*/
    }

    private int getStatusStartingIndex(Status lastStatus, List<Status> allStatuses) {//getstatusstartingindex     string alias is a status

        int followersIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i).getUserAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                    break;
                }
            }
        }

        return followersIndex;
    }

    List<Status> getDummyStatuses() {
        return Arrays.asList(stat1, stat2, stat3, stat4);
    }


    //Feed starts here---------------------------------------------------------------------------------------------------------------------
    public FeedResponse getFeed(FeedRequest request) throws IOException, TweeterRemoteException
    {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUsername() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getDummyFeed();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusIndex = getStatusStartingIndex(request.getLastStatus(), allStatuses);

            for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusIndex));
            }

            hasMorePages = statusIndex < allStatuses.size();
        }

        return new FeedResponse(responseStatuses, hasMorePages);
        /*String urlPath = "/getfeed";
        FeedResponse response = clientCommunicator.doPost(urlPath, request, null, FeedResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }*/
    }

    private int getFeedStartingIndex(Status lastStatus, List<Status> allStatuses) {//getstatusstartingindex     string alias is a status

        int followersIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i).getUserAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                    break;
                }
            }
        }

        return followersIndex;
    }

    List<Status> getDummyFeed() {
        return Arrays.asList(stat1, stat2, stat3, stat4);
    }
}
