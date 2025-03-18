package org.qbit.applicationmanager.domain.model;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserEntityTest {

    @Test
    public void testUserEntity_Creation() {
        User user = new User("testUser", "hashedPassword");

        assertThat(user, is(notNullValue()));
        assertThat(user.getUserName(), is(equalTo("testUser")));
        assertThat(user.getPasswordHash(), is(equalTo("hashedPassword")));
    }

    @Test
    public void testUserEntity_Setters() {
        User user = new User();
        user.setUserName("newUser");
        user.setPasswordHash("newHashedPassword");

        assertThat(user.getUserName(), is(equalTo("newUser")));
        assertThat(user.getPasswordHash(), is(equalTo("newHashedPassword")));
    }
}