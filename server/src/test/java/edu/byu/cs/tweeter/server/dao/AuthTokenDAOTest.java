package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthTokenDAOTest {
    AuthTokenDAO getAuthTokenDAO()
    {
        return new AuthTokenDAO();
    }

    @Test
    void create()
    {
        AuthToken authToken = getAuthTokenDAO().createAuthToken("@James");

        Assertions.assertNotEquals(null, authToken);
    }

    @Test
    void delete()
    {
        boolean authToken = getAuthTokenDAO().deleteAuthToken("@James");

        Assertions.assertNotEquals(false, authToken);
    }
}
