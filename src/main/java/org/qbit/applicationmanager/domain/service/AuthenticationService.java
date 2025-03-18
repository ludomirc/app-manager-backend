package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.User;
import java.util.Optional;

public interface AuthenticationService {
    String encodePassword(String rawPassword);
    boolean verifyPassword(String rawPassword, String encodedPassword);
    Optional<User> getUserByUserName(String userName);
}