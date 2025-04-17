package org.qbit.applicationmanager.domain.service;

public interface AuthenticationService {
    String encodePassword(String rawPassword);
    boolean verifyPassword(String rawPassword, String encodedPassword);
}