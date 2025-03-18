package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.infrastructure.http.dto.EnterpriseDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EnterpriseMapperTest {

    private final EnterpriseMapper enterpriseMapper = new EnterpriseMapper();

    @Test
    void shouldMapEnterpriseToDto() {
        // Given
        User user = new User("owner", "securepwd");
        Enterprise enterprise = new Enterprise("TechCorp", user);

        // When
        EnterpriseDto dto = enterpriseMapper.toDto(enterprise);

        // Then
        assertThat(dto, is(notNullValue()));
        assertThat(dto.getName(), is("TechCorp"));
    }

    @Test
    void shouldMapDtoToEnterprise() {
        // Given
        EnterpriseDto dto = new EnterpriseDto(1L,"TechCorp");
        User user = new User("owner", "securepwd");

        // When
        Enterprise enterprise = enterpriseMapper.fromDto(dto, user);

        // Then
        assertThat(enterprise, is(notNullValue()));
        assertThat(enterprise.getName(), is("TechCorp"));
        assertThat(enterprise.getUser(), is(user));
    }
}
