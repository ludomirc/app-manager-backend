package org.qbit.applicationmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class JwtConfig {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.issuer-uri:}")
    private String issuerUri;

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(secret.getBytes(UTF_8), "HmacSHA256");
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(key).build();

        OAuth2TokenValidator<Jwt> timestampValidator = new JwtTimestampValidator(Duration.ofMinutes(2));
        OAuth2TokenValidator<Jwt> defaultValidator =
                issuerUri.isBlank()
                        ? JwtValidators.createDefault()
                        : JwtValidators.createDefaultWithIssuer(issuerUri);

        DelegatingOAuth2TokenValidator<Jwt> validator =
                new DelegatingOAuth2TokenValidator<>(defaultValidator, timestampValidator);

        decoder.setJwtValidator(validator);
        return decoder;
    }
}
