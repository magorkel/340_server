package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PasswordDAOTest {

    PasswordDAO getPasswordDAO()
    {
        return new PasswordDAO();
    }

    @Test
    public void create()
    {
        getPasswordDAO().createPassword("me", "hi", "@harley");

        List<String> passwordAlias = getPasswordDAO().findPassword("me");

        Assertions.assertEquals("@harley", passwordAlias.get(1));
    }
}
