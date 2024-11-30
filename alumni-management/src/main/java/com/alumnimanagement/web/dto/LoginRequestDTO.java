package com.alumnimanagement.web.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String emailId;
    private String password;
}
