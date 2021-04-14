package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

import java.util.*;

public class FeedDAO
{
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private Status stat1 = new Status("@AllenAnderson @hi content1 https://google.com", user1, "Wednesday, September 22, 2021");
    //private Status stat1 = new Status("@hello @sup content1 https://google.com @hi ", user1, "Wednesday, September 22, 2021");
    private Status stat2 = new Status("hello content2", user1, "Thursday, December 4, 2021");
    private Status stat3 = new Status("hello content3", user1, "Wednesday, June 22, 2021");
    private Status stat4 = new Status("hello content4", user1, "Thursday, January 4, 2021");

    private DynamoDB dynamoDB;
    private Table table;

    public FeedDAO()
    {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-2")
                .build();

        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("feed");

        //createStatus(stat1);
        //createStatus(stat2);
        //createStatus(stat3);
        //createStatus(stat4);
        //getStatusList("@AllenAnderson");
        //deleteStatus(stat1);
    }

    public void createStatus(Status status)
    {
        try {
            System.out.println("Adding a new user...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("userAlias", status.getUser().getAlias(), "dateTime", status.getTime())
                            .withString("firstName", status.getUser().getFirstName())
                            .withString("lastName", status.getUser().getLastName())
                            .withString("imageURL", status.getUser().getImageUrl())
                            .withString("content", status.getContent()));

            System.out.println("Add status to " + status.getUser().getAlias() + "'s story succeeded:\n" + outcome.getPutItemResult());
        }
        catch (Exception e) {
            System.err.println("Unable to add status for: " + status.getUser().getAlias());
            System.err.println(e.getMessage());
        }

    }

    public List<Status> getStatusList(String userAlias)
    {
        List<Status> statuses = new ArrayList<>();

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":ua", userAlias);

        String query = "userAlias = :ua";

        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(false)
                .withKeyConditionExpression(query)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            System.out.println(userAlias + "'s statuses:");
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("userAlias"));

                User user = new User(item.getString("firstName"), item.getString("lastName"), item.getString("imageURL"));
                Status status = new Status(item.getString("content"), user, "");
                statuses.add(status);
            }

            //return followeeAliases;
        }
        catch (Exception e) {
            System.err.println("Unable to query " + userAlias + "'s statuses!");
            System.err.println(e.getMessage());
            //return ;
        }
        return statuses;
    }

    public void deleteStatus(Status status){
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("userAlias", status.getUser().getAlias(), "dateTime", status.getTime()));

        try {
//            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
        }
        catch (Exception e) {
            System.err.println("Unable to delete status: " + status.getUser().getAlias() + " " + status.getTime());
            System.err.println(e.getMessage());
        }
    }

    public PostResponse updatePostServer(PostRequest request)
    {
        //add to feed and story
        return new PostResponse(true, "successfully posted");
    }

    public FeedResponse getFeed(FeedRequest request) {
        // Used in place of assert statements because Android does not support them
        /*if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }
            if(request.getUsername() == null) {
                throw new AssertionError();
            }
        }*/
        List<Status> allStatuses = getStatusList(request.getUsername());//FIXME does this work?
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
        return Arrays.asList(stat1, stat2, stat3, stat4, stat1, stat2, stat3, stat4, stat1, stat2);
    }
}
