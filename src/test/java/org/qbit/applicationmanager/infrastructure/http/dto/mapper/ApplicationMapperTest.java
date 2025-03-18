package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.infrastructure.http.dto.ApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class ApplicationMapperTest {

    @Autowired
    ApplicationMapper applicationMapper;

    @Test
    void shouldMapApplicationToDto() {
        User user = new User("test_user", "password");
        Enterprise enterprise = new Enterprise("Acme Corp", user);
        Application application = new Application(user, enterprise, "Test application notes","Test Name");

        ApplicationDto dto = applicationMapper.toDto(application);

        assertThat(dto, is(notNullValue()));
        assertThat(dto.getEnterpriseName(), is("Acme Corp"));
        assertThat(dto.getNotes(), is("Test application notes"));
    }

    @Test
    void shouldMapDtoToApplication() {
        User user = new User("test_user", "password");
        Enterprise enterprise = new Enterprise("Acme Corp", user);
        ApplicationDto dto = new ApplicationDto(null, null, "Acme Corp", null, "Mapped notes","Test Name");

        Application application = applicationMapper.fromDto(dto, user, enterprise);

        assertThat(application, is(notNullValue()));
        assertThat(application.getUser(), is(user));
        assertThat(application.getEnterprise(), is(enterprise));
        assertThat(application.getNotes(), is("Mapped notes"));
    }
}
