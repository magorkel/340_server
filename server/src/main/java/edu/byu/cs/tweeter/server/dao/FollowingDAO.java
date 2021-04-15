package edu.byu.cs.tweeter.server.dao;

import java.util.*;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;


/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowingDAO {
    // This is the hard coded followee data returned by the 'getFollowees()' method
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

    List<User>userList = Arrays.asList(user1, user2, user3, user4, user5);

    public void populateUserList()
    {
        String allenAnderson = user1.getAlias();
        for (int i = 1; i < 5; i++)
        {
            createFollows(userList.get(i).getAlias(), allenAnderson);
        }
    }

    private DynamoDB dynamoDB;
    private Table table;

    public FollowingDAO()
    {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                //.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8080", "us-west-2"))
                .withRegion("us-west-2")
                .build();

        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("follows");

        //populateUserList();
        //getFolloweesFromDB("@AllenAnderson");
    }

    /**
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param follower the User whose count of how many following is desired.
     * @return said count.
     */
    public Integer getFolloweeCount(User follower) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert follower != null;
        return getDummyFollowees().size();
    }

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getFollowerAlias() != null;

        //FIXME how does this effect a newly registered user?
        //List<User> allFollowees = getDummyFollowees();
        List<User> allFollowees = getFolloweesFromDB(request.getFollowerAlias());
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFolloweeAlias(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    public FollowerResponse getFollower(FollowerRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getFollowerAlias() != null;

        //List<User> allFollowers = getDummyFollowees();
        List<User> allFollowers = getFollowersFromDB(request.getFollowerAlias());
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowers != null) {
                int followersIndex = getFolloweesStartingIndex(request.getLastFollowerAlias(), allFollowers);

                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                    responseFollowers.add(allFollowers.get(followersIndex));
                }

                hasMorePages = followersIndex < allFollowers.size();
            }
        }

        return new FollowerResponse(responseFollowers, hasMorePages);
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

    public List<User> getFollowersFromDB(String followeeAlias)
    {
        List<String> followers = getFollowersList(followeeAlias);
        //use followees to get list of users from DB
        List<User> users = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        for (int i = 0; i < followers.size(); i++)
        {
            users.add(userDAO.getUser(followers.get(i)));
        }
        return users;
    }

    //this one grabs the follow
    List<User> getFolloweesFromDB(String followerAlias)
    {
        List<String> followees = getFolloweesList(followerAlias);
        //System.out.println(followees.get(0));
        //use followees to get list of users from DB
        List<User> users = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        //System.out.println("before entering for loop");
        for (int i = 0; i < followees.size(); i++)
        {
            //System.out.println("i = " + i);
            users.add(userDAO.getUser(followees.get(i)));
        }
        return users;
    }

    public MakeFollowResponse updateFollowServer(MakeFollowRequest request)
    {
        //pull out two users and update their lists
        try
        {
            createFollows(request.getFollowerAlias(), request.getFolloweeAlias());
            return new MakeFollowResponse(true, "successfully followed");
        }
        catch(Exception e)
        {
            return new MakeFollowResponse(false,"error adding to table");
        }
    }
    public MakeUnfollowResponse updateUnfollowServer(MakeUnfollowRequest request)
    {
        try
        {
            deleteFollows(request.getFollowerAlias(), request.getFolloweeAlias());
            return new MakeUnfollowResponse(true,"successfully unfollowed");
        }
        catch(Exception e)
        {
            return new MakeUnfollowResponse(false, "error deleting form table");
        }
    }

    public void createFollows(String follower_handle, String followee_handle/*, String followee_name, String follower_name*/)
    {
        /*final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("follower_name", follower_name);
        infoMap.put("followee_name", followee_name);*/

        try {
            System.out.println("Adding a new item...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("follower_handle", follower_handle, "followee_handle", followee_handle));//.withMap("info", infoMap));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + follower_handle + " " + followee_handle);
            System.err.println(e.getMessage());
        }

    }

    public void deleteFollows(String follower_handle, String followee_handle){
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("follower_handle", follower_handle, "followee_handle", followee_handle));

        try {
//            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
        }
        catch (Exception e) {
            System.err.println("Unable to delete item: " + follower_handle + " " + followee_handle);
            System.err.println(e.getMessage());
        }
    }

    public List<String> getFollowersList(String followee_handle)
    {
        List<String> followeeAliases = new ArrayList<>();

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":fh", followee_handle);

        String query = "followee_handle = :fh";

        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(false)
                .withKeyConditionExpression(query)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            System.out.println(followee_handle + "'s followers:");
            items = table.getIndex("follows_index").query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("follower_handle"));

                followeeAliases.add(item.getString("follower_handle"));
            }

            //return followeeAliases;
        }
        catch (Exception e) {
            System.err.println("Unable to query " + followee_handle + "'s followers!");
            System.err.println(e.getMessage());
            //return ;
        }
        return followeeAliases;
    }

    public List<String> getFolloweesList(String follower_handle)
    {
        List<String> followerAliases = new ArrayList<>();

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":fh", follower_handle);

        String query = "follower_handle = :fh";

        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(false)
                .withKeyConditionExpression(query)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            System.out.println(follower_handle + "'s followees:");
            items = table.getIndex("follows_index").query(querySpec);



            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("followee_handle"));

                followerAliases.add(item.getString("followee_handle"));
            }

            //return followerAliases;
        }
        catch (Exception e) {
            System.err.println("Unable to query " + follower_handle + "'s followees!");
            System.err.println(e.getMessage());
            //return null;
        }
        return followerAliases;
    }
}
