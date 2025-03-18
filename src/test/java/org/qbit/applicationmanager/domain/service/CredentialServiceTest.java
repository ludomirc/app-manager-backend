package org.qbit.applicationmanager.domain.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CredentialServiceTest {

    private final CredentialService credentialService = new CredentialService();

    @Test
    public void testPasswordEncodingAndMatching() {
        String rawPassword = "secure_password";
        String encodedPassword = credentialService.encodePassword(rawPassword);

        assertThat(encodedPassword).isNotEqualTo(rawPassword); // Password should be hashed
        assertThat(credentialService.matches(rawPassword, encodedPassword)).isTrue(); // Should match
        assertThat(credentialService.matches("wrong_password", encodedPassword)).isFalse(); // Should not match
    }
}