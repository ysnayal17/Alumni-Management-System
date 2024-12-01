package com.alumnimanagement.web.controller;

import com.alumnimanagement.entity.Job;
import com.alumnimanagement.services.def.JobService;
import com.alumnimanagement.web.dto.APIResponseDTO;
import com.alumnimanagement.web.dto.JobDTO;
import com.alumnimanagement.web.dto.UserDTO;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);
    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ALUMNI')")
    public ResponseEntity<APIResponseDTO<String>> createJob(@Valid @RequestBody JobDTO jobDTO){
        this.jobService.createJob(jobDTO);
        return new ResponseEntity<>(APIResponseDTO.<String>builder()
                .payload("Job created successfully")
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN','ALUMNI','STUDENT')")
    public ResponseEntity<APIResponseDTO<List<JobDTO>>> getUserList(){

        return new ResponseEntity<>(APIResponseDTO.<List<JobDTO>>builder()
                .payload(this.jobService.getAllJobs())
                .build(),HttpStatus.OK);
    }
}
