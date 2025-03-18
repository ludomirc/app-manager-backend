package org.qbit.applicationmanager.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApplicationEntityTest {

    @Test
    public void testApplicationEntity_Creation() {
        User user = new User("testUser", "hashedPassword");
        Enterprise enterprise = new Enterprise("TestEnterprise", user);
        LocalDateTime now = LocalDateTime.now();

        Application application = new Application(user, enterprise, "TestNotes","Test Name");

        assertThat(application, is(notNullValue()));
        assertThat(application.getUser(), is(notNullValue()));
        assertThat(application.getEnterprise(), is(notNullValue()));
        assertThat(application.getNotes(), is(equalTo("TestNotes")));
        assertThat(application.getCreationDate(), is(notNullValue()));
        assertThat(application.getCreationDate(), is(lessThanOrEqualTo(now.plusSeconds(1))));
    }
}
