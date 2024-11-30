package com.alumnimanagement.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {

    private final KeycloakConfigurationProperties keycloakConfigurationProperties;

    @Autowired
    public KeycloakConfiguration(KeycloakConfigurationProperties keycloakConfigurationProperties) {
        this.keycloakConfigurationProperties = keycloakConfigurationProperties;
    }

    @Bean
    protected Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .grantType(keycloakConfigurationProperties.getGrantType())
                .realm(keycloakConfigurationProperties.getApplicationRealm())
                .clientId(keycloakConfigurationProperties.getClientId())
                .username(keycloakConfigurationProperties.getUsername())
                .password(keycloakConfigurationProperties.getPassword())
                .serverUrl(keycloakConfigurationProperties.getServerBaseUrl())
                .build();
    }
}
