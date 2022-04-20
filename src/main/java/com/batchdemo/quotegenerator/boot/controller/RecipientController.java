package com.batchdemo.quotegenerator.boot.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sendEmails")
public class RecipientController {

    private JobLauncher jobLauncher;
    private Job job;

    @Autowired
    public RecipientController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping
    private String sendEmails() throws JobInstanceAlreadyCompleteException,
                                        JobExecutionAlreadyRunningException,
                                        JobParametersInvalidException,
                                        JobRestartException {
        Map<String, JobParameter> paramsMap = new HashMap<>();
        paramsMap.put("quote", new JobParameter("You can do it!"));

        JobParameters params = new JobParameters(paramsMap);
        JobExecution jobExecution = jobLauncher.run(job, params);

        System.out.println("Started Job execution!");

        while(jobExecution.isRunning()) {
            System.out.println("Job is still running...");
        }

        return jobExecution.getStatus().toString();
    }
}
