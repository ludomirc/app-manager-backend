package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialService credentialService;

    public User registerUser(String userName, String rawPassword) {
        String hashedPassword = credentialService.encodePassword(rawPassword);
        User user = new User(userName, hashedPassword);
        return userRepository.save(user);
    }

    public boolean authenticateUser(String userName, String rawPassword) {
        User user = userRepository.findByUserName(userName);
        return user != null && credentialService.matches(rawPassword, user.getPasswordHash());
    }
}