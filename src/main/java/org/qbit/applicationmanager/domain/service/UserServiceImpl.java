package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public UserServiceImpl(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public User registerUser(String userName, String rawPassword) {
        String hashedPassword = authenticationService.encodePassword(rawPassword);
        User user = new User(userName, hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public boolean authenticateUser(String userName, String rawPassword) {
        User user = userRepository.findByUserName(userName);
        return user != null && authenticationService.verifyPassword(rawPassword, user.getPasswordHash());
    }

    public User getUserByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }
}