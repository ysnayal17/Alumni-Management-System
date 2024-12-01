package com.alumnimanagement.services.def;

import com.alumnimanagement.web.dto.JobDTO;

import java.util.List;

public interface JobService {

    void createJob(JobDTO jobDTO);
    List<JobDTO> getAllJobs();
}
