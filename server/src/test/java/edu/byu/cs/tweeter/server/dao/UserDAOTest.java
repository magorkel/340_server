package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDAOTest {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user2 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user3 = new User("Alen", "Hendriks", MALE_IMAGE_URL);
    private final User user4 = new User("Harley", "C", MALE_IMAGE_URL);
    UserDAO getUserDAO() {
        return new UserDAO();
    }

    @Test
    void register()
    {
        getUserDAO().registerUser(user3, "als");
        getUserDAO().registerUser(user4, "allsso");

        boolean registered = getUserDAO().registerUser(user2, "helloworld");
        Assertions.assertEquals(false, registered);

    }
}
