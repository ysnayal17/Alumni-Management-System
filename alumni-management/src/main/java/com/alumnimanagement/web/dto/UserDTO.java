package com.alumnimanagement.web.dto;

import com.alumnimanagement.enums.Department;
import com.alumnimanagement.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private String id;
    private String name;
    private LocalDate dateOfGraduation;
    private Department department;
    private String mobileNo;
    private String linkedinProfile;
    private Role role;
    private String city;
    private String state;
    private String country;
    private String companyName;
    private MultipartFile image;
    private String skills;
    private String email;
    private byte[] profileImage;
}
