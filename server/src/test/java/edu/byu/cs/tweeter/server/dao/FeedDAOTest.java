package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FeedDAOTest {
    String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    User user1 = new User("Amy", "Anderson", MALE_IMAGE_URL);
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    Status stat1 = new Status("@AllenAnderson @hi content1 https://google.com", user1, "Wednesday, September 22, 2021");
    Status stat2 = new Status("hi", user1, "today");
    Status stat3 = new Status("hi", user1, "yesterday");
    Status stat4 = new Status("hi", user1, "tomorrow");
    Status stat5 = new Status("hi", user1, "next weekend");

    FeedDAO getFeedDAO() {
        return new FeedDAO();
    }

    @Test
    public void create()
    {
        getFeedDAO().createStatus(stat1, user1.getAlias());
        //getFeedDAO().createStatus(stat2, user2.getAlias());
        //getFeedDAO().createStatus(stat3, user2.getAlias());
        //getFeedDAO().createStatus(stat4, user2.getAlias());
        //getFeedDAO().createStatus(stat5, user2.getAlias());

        List<Status> statusList = getFeedDAO().getStatusList(user1.getAlias());

        boolean found = false;
        for (int i = 0; i < statusList.size(); i++)
        {
            if (statusList.get(i).getUserAlias().equals(user1.getAlias()))
            {
                found = true;
            }
        }

        Assertions.assertEquals(true, found);
    }
}
