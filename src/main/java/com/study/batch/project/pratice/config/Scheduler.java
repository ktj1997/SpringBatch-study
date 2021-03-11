package com.study.batch.project.pratice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class Scheduler {

    private final JobLauncher jobLauncher;
    private final JobConfiguration jobConfiguration;

    @Scheduled(cron = "0 0/1 * * * MON-FRI")
    public void scheduler() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        long start = System.currentTimeMillis();
        System.out.println("CronJob start");
        Map<String, JobParameter> timeMap = new HashMap<>();
        timeMap.put("time", new JobParameter(new Date()));
        jobLauncher.run(jobConfiguration.sequenceJob(), new JobParameters(timeMap));
        System.out.println("CronJob Finish" + " " + (System.currentTimeMillis() - start));
    }
}
