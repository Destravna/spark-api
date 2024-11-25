package org.dhruv.sparkapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.dhruv.sparkapi.service.HandleAddExtract;
import org.dhruv.sparkapi.service.SparkJobLauncherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.dhruv.sparkapi.service.ExtractJobService;
import org.dhruv.sparkapi.entity.Extract;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Adjust this to your frontend URL
public class JobController {
    @Autowired
    private SparkJobLauncherService sparkJobLauncherService;
    @Autowired
    private HandleAddExtract handleAddExtract;
    @Autowired
    private ExtractJobService extractJobService;
    @GetMapping("/run-job")
    public ResponseEntity<String> getMethodName() {
        // sparkJobLauncherService.launchSampleSparkJob();
        // sparkJobLauncherService.commandRunner();
        // sparkJobLauncherService.sparkSessionTest();
        return ResponseEntity.ok("working\n");
    }

    @PostMapping("/add-extract")
    public ResponseEntity<String> postMethodName(@RequestBody String entity) {
        boolean valid = handleAddExtract.startExtract(entity);
        if(!valid){
            return ResponseEntity.badRequest().body("Please provide valid fields");
        }
        System.out.println(entity);
        // sparkJobLauncherService.launchSampleSparkJob();
        
        return ResponseEntity.ok("extract-started");
    }

    @GetMapping("/get-all-jobs")
    public ResponseEntity<List> getAllJobs(){
        List<Extract> jobs = extractJobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

}
