package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private static final int WORK_FACTOR_STRENGTH = 12;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder(WORK_FACTOR_STRENGTH);
        this.userRepository = userRepository;
    }

    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }
}