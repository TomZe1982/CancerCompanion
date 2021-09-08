package de.tomze.backend.security;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="security.jwt")
@Data
public class JwtConfig {

    String secret;
    int expireAfterMinutes;

}
