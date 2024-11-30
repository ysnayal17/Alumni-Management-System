package com.alumnimanagement.web.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class KeycloakUserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
}
