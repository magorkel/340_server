package edu.byu.cs.tweeter.server.service;

import com.google.gson.Gson;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.domain.UserListStorage;
import edu.byu.cs.tweeter.server.dao.FeedDAO;

import java.util.List;

public class JobsPusher {
    public void getPosts(String jsonIn) {
        Gson gson = new Gson();
        //We need to Grab all followers
        UserListStorage userListStorage = gson.fromJson(jsonIn, UserListStorage.class);
        List<User> userList = userListStorage.getUserList();
        Status status = userListStorage.getStatus();
        for (int i = 0; i < userList.size(); i++) {
            getFeedDAO().createStatus(status, userList.get(i).getAlias());
        }
    }
    FeedDAO getFeedDAO() {
        return new FeedDAO();
    }
}
