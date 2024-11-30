package com.alumnimanagement.web.dto;

import com.alumnimanagement.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class UserDTO {

    private String emailId;
    private String name;
    private LocalDate dateOfGraduation;
    private Role role;
}
