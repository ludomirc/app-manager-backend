package org.qbit.applicationmanager.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.qbit.applicationmanager.domain.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

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
}


