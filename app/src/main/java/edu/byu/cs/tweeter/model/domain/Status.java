package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Status implements Comparable<Status>, Serializable
{
    private ArrayList<String> tag;//name of person referenced inside of post FIXME: These are supposed to be arrays?
    private ArrayList<String> URL;//link to post
    private String content;
    private User user;//alias and avatar/pic
    private String time;//need time date object

    public Status(String content, User user, String time) {//have this parse out the tag and URL
        this.content = content;//parse out tag and URL if they exist
        this.user = user;
        this.time = time;
        this.tag = new ArrayList<>();
        this.URL = new ArrayList<>();
        parseContent(content);
    }

    public void parseContent(String content) {
        String delims = "[ ]+";
        String[] tokens = content.split(delims);

        if (content != null) {
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].charAt(0) == '@') {
                    //Append "Post Mention: " or "URL: " - or have it do it when we call display on this information in other class.
                    this.tag.add(tokens[i]);
                } else {//FIXME: Make it so this parses for URLS's as well.
                    this.URL.add(tokens[i]);
                }
            }
        }
    }

    public ArrayList<String> getTag() { return tag; }
    public ArrayList<String> getURL() { return URL; }
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
