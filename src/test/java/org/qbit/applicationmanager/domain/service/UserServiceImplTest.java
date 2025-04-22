package org.qbit.applicationmanager.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "hashedPassword");
    }

    @Test
    void shouldRegisterUser() {
        when(authenticationService.encodePassword("rawPassword")).thenReturn("hashedPassword");
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser("testUser", "rawPassword");

        assertThat(registeredUser, notNullValue());
        assertThat(registeredUser.getUserName(), equalTo("testUser"));
        assertThat(registeredUser.getPasswordHash(), equalTo("hashedPassword")); // Ensuring password is hashed
    }
}
