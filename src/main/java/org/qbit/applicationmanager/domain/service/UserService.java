package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.User;

import java.util.Optional;

public interface UserService {
    User registerUser(String userName, String rawPassword);
    Optional<User> getUserByUserName(String userName);
}