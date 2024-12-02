package org.dhruv.sparkapi.service;

import org.dhruv.sparkapi.entity.Extract;
import org.dhruv.sparkapi.entity.Extracts;
import org.dhruv.sparkapi.repository.ExtractJobRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ExtractJobService {
    Logger logger = LoggerFactory.getLogger(ExtractJobService.class);
    @Autowired
    private ExtractJobRepo extractJobRepo;

    public Extract insertJob(Extracts extract){
        Extract extract2 = new Extract(extract);
        extract2 = extractJobRepo.save(extract2);
        logger.info("ExtractJob added : {}", extract2);
        return extract2;
    }

    public void updateJobStatusById(Extract extractJob, String status){
        Extract existingExtractJob = extractJobRepo.findById(extractJob.getJobId())
        .orElseThrow(() -> new IllegalArgumentException("Extract not found with ID: " + extractJob.getJobId()));
        existingExtractJob.setExtractStatus(status);
        Extract jobWithNewStatus = extractJobRepo.save(existingExtractJob);
        logger.info("extract status changed : {}", jobWithNewStatus.toString());
    }

    public List<Extract> getAllJobs(){
        return extractJobRepo.findAllByOrderByJobIdDesc();
    }

    public void updateJobFileLocationById(Extract extractJob ){
        Extract existingExtractJob = extractJobRepo.findById(extractJob.getJobId())
        .orElseThrow(() -> new IllegalArgumentException("Extract not found with ID: " + extractJob.getJobId())); 
        existingExtractJob.setFile("http://localhost:9870/explorer.html#/home/dhruv/projects/sample-spark-job/output" + "_" + extractJob.getExtractName() + "_" + LocalDate.now().toString());  
        Extract extractWithFile = extractJobRepo.save(existingExtractJob);
        logger.info("Extract file location saved : {}", extractWithFile);

    }
    
}
