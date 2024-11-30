package com.alumnimanagement.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
@Data
public class KeycloakConfigurationProperties {

    private String applicationRealm;
    private String clientId;
    private String username;
    private String password;
    private String serverBaseUrl;
    private String grantType;
    private String realmDefaultRole;
    private String realmClientId;
    private String realmClientSecret;
}
