package edu.byu.cs.tweeter.model.domain;

public class Status
{
    private String tag;//name of person referenced inside of post
    private String[] URL;//link to post

    public void setTag(String tag) { this.tag = tag; }
    public void setURL(String[] URL) { this.URL = URL; }
    //need individual setURL?

    public String getTag() { return tag; }
    public String[] getURL() { return URL; }
    //need print maybe for URL? gets individual
}
