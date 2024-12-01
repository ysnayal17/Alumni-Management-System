package com.alumnimanagement.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDTO {

    private String id;
    private String companyName;
    private String requiredSkills;
    private String description;
    private LocalDate postDate;
    private LocalDate lastDate;
    private String userId;
    private UserDTO createdBy;
    private Integer noOfOpenPositions;
}
