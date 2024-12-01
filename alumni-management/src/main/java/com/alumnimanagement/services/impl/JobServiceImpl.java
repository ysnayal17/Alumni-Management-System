package com.alumnimanagement.services.impl;

import com.alumnimanagement.entity.Job;
import com.alumnimanagement.entity.User;
import com.alumnimanagement.exception.UserNotFoundException;
import com.alumnimanagement.repo.JobRepository;
import com.alumnimanagement.repo.UserRepository;
import com.alumnimanagement.services.def.JobService;
import com.alumnimanagement.web.dto.JobDTO;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class JobServiceImpl implements JobService {

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final MapperFacade mapperFacade;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, UserRepository userRepository, MapperFacade mapperFacade) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public void createJob(JobDTO jobDTO) {
        User user = userRepository.findById(jobDTO.getUserId()).orElseThrow(
                ()-> new UserNotFoundException("User not exist in db"));
        Job job = Job.builder()
                .id(UUID.randomUUID().toString())
                .createdBy(user)
                .companyName(jobDTO.getCompanyName())
                .description(jobDTO.getDescription())
                .lastDate(jobDTO.getLastDate())
                .postDate(jobDTO.getPostDate())
                .noOfOpenPositions(jobDTO.getNoOfOpenPositions())
                .requiredSkills(jobDTO.getRequiredSkills())
                .build();
        this.jobRepository.save(job);
    }

    @Override
    @Transactional
    public List<JobDTO> getAllJobs() {
        List<Job> jobList = this.jobRepository.findAll();
        return jobList.stream().map(job->
                mapperFacade.map(job, JobDTO.class)).toList();
    }
}
