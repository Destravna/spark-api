package org.dhruv.sparkapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.dhruv.sparkapi.service.SparkJobLauncherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class JobController {
    @Autowired
    private SparkJobLauncherService sparkJobLauncherService;

    @GetMapping("/run-job")
    public ResponseEntity<String> getMethodName() {
        // sparkJobLauncherService.launchSampleSparkJob();
        // sparkJobLauncherService.commandRunner();
        // sparkJobLauncherService.sparkSessionTest();
        return ResponseEntity.ok("working\n");
    }
    
}
