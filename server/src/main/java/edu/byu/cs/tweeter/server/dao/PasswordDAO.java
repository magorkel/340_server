package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PasswordDAO
{
    private DynamoDB dynamoDB;
    private Table table;

    public PasswordDAO()
    {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-2")
                .build();

        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("password");

        //createPassword("b", "hi", "@AmyAmes");
    }

    public boolean createPassword(String userName, String password, String userAlias)
    {
        //adds them both to the table and returns true if they were added ok
        try {
            System.out.println("Adding a new password...");
            String salt = getSalt();
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("userName", userName)
                            .withString("password", getSecurePassword(password, salt))
                            .withString("userAlias", userAlias)
                            .withString("salt", salt));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
            return true;
        }
        catch (Exception e) {
            System.err.println("Unable to add password for: " + userAlias);
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<String> findPassword(String userName)
    {
        List<String> retString = new ArrayList<>();
        //returns the password associated with the user
        //find user here
        Item item = null;

        try {
            System.out.println(userName);
            item = table.getItem("userName", userName);
            System.out.println(item.getString("userName"));

            retString.add(item.getString("password"));
            retString.add(item.getString("userAlias"));
            retString.add(item.getString("salt"));
        }
        catch (Exception e) {
            System.err.println("Unable to query for " + userName);
            System.err.println(e.getMessage());
        }
        return retString;
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

    private static String getSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "FAILED TO GET SALT";
    }
}