package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.PostService;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;

public class PostServiceImpl implements PostService {
    @Override
    public PostResponse updatePostServer(PostRequest request) {

        String messageBody = "*** PUT YOUR MESSAGE BODY HERE ***";
        String queueUrl = "*** PUT YOUR QUEUE URL HERE ***";

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(messageBody)
                .withDelaySeconds(5);

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