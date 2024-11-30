package com.alumnimanagement.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakJwtTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String REALM_ACCESS = "realm_access";
    private static final String ROLES = "roles";
    private static final String ROLE_PREFIX = "ROLE_";
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
    private final KeycloakConfigurationProperties properties;

    public KeycloakJwtTokenConverter(
            JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter,
            KeycloakConfigurationProperties properties) {
        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
        this.properties = properties;
    }


    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        Stream<SimpleGrantedAuthority> accesses = Optional.of(jwt)
                .map(token -> token.getClaimAsMap(REALM_ACCESS))
                .map(resourceData -> (Collection<String>) resourceData.get(ROLES))
                .stream()
                .flatMap(Collection::stream)
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .distinct();
        Set<GrantedAuthority> authorities = Stream
                .concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(), accesses)
                .collect(Collectors.toSet());

        String principalClaimName = Optional.of("preferred_username")
                .map(jwt::getClaimAsString)
                .orElse(jwt.getClaimAsString(JwtClaimNames.SUB));

        return new JwtAuthenticationToken(jwt, authorities, principalClaimName);
    }
}

