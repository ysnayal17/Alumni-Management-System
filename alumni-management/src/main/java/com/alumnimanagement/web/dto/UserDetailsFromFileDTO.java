package com.alumnimanagement.web.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserDetailsFromFileDTO {
    @NotNull(message = "Please provide file to add user details")
    private MultipartFile file;
}
