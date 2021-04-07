package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

import java.io.IOException;
import java.util.*;

public class UserDAO
{
    private DynamoDB dynamoDB;
    private Table table;

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

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

    private ArrayList<User> userList = new ArrayList<>();

    public UserDAO()
    {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                //.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8080", "us-west-2"))
                .withRegion("us-west-2")
                .build();

        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("user");



        List<User> listOfUsers = getDummyUsers();
        for (int i = 0; i < listOfUsers.size(); i ++)
        {
            //createUser(listOfUsers.get(i));
        }
        //createUser(user1);
    }

    /*public Integer getUserCount(User user) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert user != null;
        return getDummyUsers().size();
    }*/

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param userAlias contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public User getUser(String userAlias) {
        //query statement returns the given user based on the userName;
        User user = new User();

        Item item = null;

        try {
            System.out.println(userAlias);
            item = table.getItem("alias", userAlias);
            System.out.println(item.getString("firstName"));

            user.setAlias(userAlias);
            user.setFirstName(item.getString("firstName"));
            user.setLastName(item.getString("lastName"));
            user.setImageUrl(item.getString("imageURL"));

            return user;
        }
        catch (Exception e) {
            System.err.println("Unable to query for " + userAlias);
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void createUser(User user)
    {
        try {
            System.out.println("Adding a new user...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("alias", user.getAlias())
                            .withString("firstName", user.getFirstName())
                            .withString("lastName", user.getLastName())
                            .withString("imageURL", user.getImageUrl()));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        }
        catch (Exception e) {
            System.err.println("Unable to add user: " + user.getAlias());
            System.err.println(e.getMessage());
        }
    }

    public boolean registerUser(User user, String userName) //need key value pair to match userName to user
    {
        ArrayList taken = new ArrayList<String> ();
        taken.add("freddyJ");
        taken.add("daphneB");
        taken.add("velmaD");
        taken.add("scoobyD");
        boolean unique = true;
        for (int i = 0; i < taken.size(); i++) {
            if (userName.equals(taken.get(i))) {
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
            //User user = new User(request.getFirstName(), request.getLastName(), "@" + request.getFirstName() + request.getLastName(),
                    //request.getImage()/*newBitmap*/);//decode back into a bitearray and then do all that above
            //return new RegisterResponse(user, new AuthToken());
            return true;
        }
        else {
            return false;
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
    /*private int getFolloweesStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {

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
    }*/

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyUsers() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    public UserResponse findUser(UserRequest request) {
        if (request == null)
        {
            return new UserResponse("improper request");
        }
        //check through db of users
        userList.add(user1);
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
        //else return error
    }
}
