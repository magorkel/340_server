package edu.byu.cs.tweeter.model.domain;

import java.util.List;

public class UserListStorage {
    private List<User> userList;
    private Status status;
    public  UserListStorage() {}
    public UserListStorage(List<User> userList, Status status) {
        this.userList = userList;
        this.status = status;
    }
    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
