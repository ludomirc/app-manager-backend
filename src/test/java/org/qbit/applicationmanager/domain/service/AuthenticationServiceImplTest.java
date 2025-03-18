package org.qbit.applicationmanager.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.repository.UserRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEncodePassword() {
        String rawPassword = "secure_password";
        String encodedPassword = authenticationService.encodePassword(rawPassword);

        assertThat(encodedPassword, is(not(equalTo(rawPassword))));
    }

    @Test
    public void testVerifyPassword_CorrectPassword() {
        String rawPassword = "password123";
        String encodedPassword = authenticationService.encodePassword(rawPassword);

        boolean isMatch = authenticationService.verifyPassword(rawPassword, encodedPassword);

        assertThat(isMatch, is(true));
    }

    @Test
    public void testVerifyPassword_IncorrectPassword() {
        String rawPassword = "password123";
        String encodedPassword = authenticationService.encodePassword(rawPassword);

        boolean isMatch = authenticationService.verifyPassword("wrong_password", encodedPassword);

        assertThat(isMatch, is(false));
    }

    @Test
    public void testGetUserByUserName_UserExists() {
        User user = new User("testUser", "hashedPassword");
        when(userRepository.findByUserName("testUser")).thenReturn(user);

        Optional<User> foundUser = authenticationService.getUserByUserName("testUser");

        assertThat(foundUser.isPresent(), is(true));
        assertThat(foundUser.get().getUserName(), is(equalTo("testUser")));

        verify(userRepository, times(1)).findByUserName("testUser");
    }

    @Test
    public void testGetUserByUserName_UserNotFound() {
        when(userRepository.findByUserName("unknownUser")).thenReturn(null);

        Optional<User> foundUser = authenticationService.getUserByUserName("unknownUser");
        assertThat(foundUser.isPresent(), is(false));

        verify(userRepository, times(1)).findByUserName("unknownUser");
    }
}