package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import edu.byu.cs.tweeter.model.service.PostService;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;

public class PostServiceImpl implements PostService {
    @Override
    public PostResponse updatePostServer(PostRequest request) {
        Gson gson = new Gson();
        //Staff obj = new Staff();
        // 1. Java object to JSON file
        //gson.toJson(request.getPostStatus(), new FileWriter("C:\\projects\\staff.json"));
        // 2. Java object to JSON string
        String jsonInString = gson.toJson(request.getPostStatus());
        //String messageBody = jsonInString;
        String queueUrl = "https://sqs.us-west-2.amazonaws.com/804720584476/postQueue";
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(jsonInString);
        //.withDelaySeconds(5);
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
        String msgId = send_msg_result.getMessageId();
        System.out.println("Message ID: " + msgId);
        return getStatusDAO().updatePostServer(request);
    }
    /**
     * Returns an instance of {@link FollowingDAO}. Allows mocking of the FollowingDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowingDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    StatusDAO getStatusDAO() {
        return new StatusDAO();
    }
}