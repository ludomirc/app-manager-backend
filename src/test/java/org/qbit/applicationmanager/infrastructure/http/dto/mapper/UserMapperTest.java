package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.infrastructure.http.dto.UserDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserMapperTest {

    private static UserMapper userMapper = new UserMapper();

    @Test
    void shouldMapUserToUserDto() {
        // Given
        User user = new User("john_doe", "hashed_password");
        user.setUserName("john_doe");

        // When
        UserDto dto = userMapper.toDto(user);

        // Then
        assertThat(dto, is(notNullValue()));
        assertThat(dto.getUserName(), is("john_doe"));
    }

    @Test
    void shouldMapUserDtoToUser() {
        // Given
        UserDto dto = new UserDto("john_doe","raw_password");

        // When
        User user = userMapper.fromDto(dto);

        // Then
        assertThat(user, is(notNullValue()));
        assertThat(user.getUserName(), is("john_doe"));
        assertThat(user.getPasswordHash(), is(emptyOrNullString()));
    }
}
