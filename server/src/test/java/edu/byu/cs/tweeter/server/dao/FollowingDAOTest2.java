package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FollowingDAOTest2 {
    FollowingDAO getFollowingDAO() {
        return new FollowingDAO();
    }

    @Test
    void testGetFollowers()
    {
        List<User> followers = getFollowingDAO().getFollowersFromDB("@AllenAnderson");
        for (int i = 0; i < followers.size(); i++)
        {
            System.out.println(followers.get(i).getAlias());
        }
    }

    @Test
    void testGetFollowees()
    {
        List<User> followees = getFollowingDAO().getFolloweesFromDB("@AllenAnderson");
        for (int i = 0; i < followees.size(); i++)
        {
            System.out.println(followees.get(i).getAlias());
        }
        Assertions.assertEquals(0, followees.size());
    }

    @Test
    void create()
    {
        getFollowingDAO().createFollows("@AmyAmes", "@AlenHendriks");
        getFollowingDAO().createFollows("@HarleyC", "@AmyAmes");
        getFollowingDAO().createFollows("@AmyAmes", "@AllenAnderson");

        List<User> followees = getFollowingDAO().getFolloweesFromDB("@AmyAmes");
        for (int i = 0; i < followees.size(); i++)
        {
            System.out.println(followees.get(i).getAlias());
        }
        Assertions.assertEquals(0, followees.size());
    }
}
