package org.qbit.applicationmanager.domain.model;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EnterpriseEntityTest {

    @Test
    public void testEnterpriseEntity_Creation() {
        User user = new User("testUser", "hashedPassword");
        Enterprise enterprise = new Enterprise("TestEnterprise", user);

        assertThat(enterprise, is(notNullValue()));
        assertThat(enterprise.getName(), is(equalTo("TestEnterprise")));
        assertThat(enterprise.getUser(), is(notNullValue()));
        assertThat(enterprise.getUser().getUserName(), is(equalTo("testUser")));
    }
}