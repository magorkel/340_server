package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.List;

public class Status implements Comparable<Status>, Serializable
{
    private String[] tag;//name of person referenced inside of post
    private String[] URL;//link to post
    private String content;
    private User user;//alias and avatar/pic
    private String time;//need time date object

    public Status(String content, User user, String time) {//have this parse out the tag and URL
        this.content = content;//parse out tag and URL if they exist
        this.user = user;
        this.time = time;

        this.tag = null;
        this.URL = null;
    }

    public String[] getTag() { return tag; }
    public String[] getURL() { return URL; }
    public String getContent() { return content; }
    public User getUser() { return user; }
    public String getUserAlias() { return user.getAlias(); }
    public String getTime() { return time; }

    @Override
    public int compareTo(Status status)//user, content, time
    {
        return this.getContent().compareTo(status.getContent());
    }
    //need print maybe for URL? gets individual
}
