package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.User;

public interface UserService {
    User registerUser(String userName, String rawPassword);
    boolean authenticateUser(String userName, String rawPassword);
    User getUserByUsername(String userName);
}