package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.infrastructure.http.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(user.getUserName(), null);
    }

    public User fromDto(UserDto dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        return user;
    }
}
