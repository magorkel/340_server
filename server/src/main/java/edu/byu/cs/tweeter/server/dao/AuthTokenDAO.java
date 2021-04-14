package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import edu.byu.cs.tweeter.model.domain.AuthToken;

import java.nio.charset.Charset;
import java.util.Random;

public class AuthTokenDAO
{
    private DynamoDB dynamoDB;
    private Table table;

    public AuthTokenDAO()
    {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-2")
                .build();

        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("authToken");

        //createAuthToken("@AmyAmes");
        //deleteAuthToken("y");
    }
    //create
    //find
    //delete
    public AuthToken createAuthToken(String userAlias)
    {
        try {
            AuthToken authToken = new AuthToken();
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            authToken.setAuthToken(generatedString);

            System.out.println("Adding a new authToken...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("userAlias", userAlias)
                            .withString("authToken", authToken.getAuthToken()));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

            return authToken;
        }
        catch (Exception e) {
            System.err.println("Unable to add user: " + userAlias);
            System.err.println(e.getMessage());

            return null;
        }
        //create a new random authToken and add it to the table associated with the userName
        //AuthToken authToken = new AuthToken("hi");
    }

    public boolean findAuthToken(AuthToken authToken)
    {
        //is this a valid authToken? does it exist in the table - does it need to take in a user with it? so they match up?
        return true;
    }

    public boolean deleteAuthToken(String userAlias) //FIXME does this need to be an authToken?
    {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("userAlias", userAlias));

        try {
//            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
            return true;
        }
        catch (Exception e) {
            System.err.println("Unable to delete status: " + userAlias);
            System.err.println(e.getMessage());

            return false;
        }
        //will it take in an AuthToken or a username
    }
}
