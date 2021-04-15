package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.domain.UserListStorage;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

import java.util.ArrayList;
import java.util.List;

public class FollowFetcher {

    public void findFollowers(String jsonStatus) {
        System.out.println("entering followfetcher");
        Gson gson = new Gson();
        //We need to Grab all followers
        Status status = gson.fromJson(jsonStatus, Status.class);
        User currUser = status.getUser();
        List<User> followerList = getFollowingDAO().getFollowersFromDB(currUser.getAlias());
        List<User> batchSize = new ArrayList<>();
        for (int i = 0; i < followerList.size(); i++) {
            batchSize.add(followerList.get(i));
            //write units of size 100 (on DynamoDB).
            if (batchSize.size() >= 25) {
                //Send URL query of given size
                UserListStorage listToSend = new UserListStorage(batchSize, status);
                Gson gson2 = new Gson();
                String jsonUserListStorage = gson2.toJson(listToSend);
                String queueUrl = "https://sqs.us-west-2.amazonaws.com/804720584476/jobQueue";
                SendMessageRequest send_msg_request = new SendMessageRequest()
                        .withQueueUrl(queueUrl)
                        .withMessageBody(jsonUserListStorage);
                //.withDelaySeconds(5);
                AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
                SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
                String msgId = send_msg_result.getMessageId();
                System.out.println("Sent Batch " + i + " : " + msgId);
                batchSize = new ArrayList<>();
                //Reset size of query list.
            }
        }
        //Finally, if we've escaped the list, and batchSize is greater than 0, then send it again.
        if (batchSize.size() > 0) {
            UserListStorage listToSend = new UserListStorage(batchSize, status);
            Gson gson2 = new Gson();
            String jsonUserListStorage = gson2.toJson(listToSend);
            String queueUrl = "https://sqs.us-west-2.amazonaws.com/804720584476/jobQueue";
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(jsonUserListStorage);
            //.withDelaySeconds(5);
            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
            SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
            String msgId = send_msg_result.getMessageId();
            System.out.println("Sent Batch : " + msgId);
            batchSize = new ArrayList<>();
        }
    }
    FollowingDAO getFollowingDAO() {
        return new FollowingDAO();
    }
    FeedDAO getFeedDAO() {
        return new FeedDAO();
    }
}
