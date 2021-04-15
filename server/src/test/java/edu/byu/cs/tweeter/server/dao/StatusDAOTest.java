package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StatusDAOTest {
    String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    User user1 = new User("Amy", "Anderson", MALE_IMAGE_URL);
    Status stat1 = new Status("@AllenAnderson @hi content1 https://google.com", user1, "Wednesday, September 22, 2021");

    StatusDAO getStatusDAO() {
        return new StatusDAO();
    }

    @Test
    public void create()
    {
        getStatusDAO().createStatus(stat1);

        List<Status> statusList = getStatusDAO().getStatusList(user1.getAlias());

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
