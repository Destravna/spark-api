
package org.dhruv.sparkapi.service;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkAppHandle.Listener;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.dhruv.sparkapi.entity.Extract;
import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SparkJobLauncherService {
    private Logger log = LoggerFactory.getLogger(SparkJobLauncherService.class);

    @Autowired 
    private ExtractJobService extractJobService;

    public void sparkSessionTest() {
        SparkSession sparkSession = SparkSession.builder().master("spark://dhruv-VMware-Virtual-Platform:7077")
                .appName("spark-test").enableHiveSupport().getOrCreate();
        Dataset<Row> df = sparkSession.sql("select * from default.employee");
        df.show();
    }

    public void commandRunner() {
        ProcessBuilder processBuilder = new ProcessBuilder("ls", "-l");
        processBuilder.directory(new File("/home/dhruv"));
        System.out.println("directory: " + processBuilder.directory());
        StringBuilder output = new StringBuilder();
        try {
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            System.out.println(output.toString());

        } catch (IOException e) {
            log.error("Process Builder failed");
        }

    }

    public void launchSparkJobWithGivenJson(String jsonPath, Extract extract) {
        System.out.println("In spark Application Launcher");
        SparkLauncher sparkLauncher = new SparkLauncher()
                .setAppResource("/home/dhruv/projects/sample-spark-job/sparkjob/target/jobj.jar")
                .setConf("spark.driver.extraClassPath",
                        "/home/dhruv/Downloads/gson-2.10.jar:/opt/spark/jars/gson-2.2.4.jar")
                .addJar("/home/dhruv/Downloads/gson-2.10.jar")
                .addJar("/home/dhruv/projects/sample-spark-job/spark-xml_2.12-0.18.0.jar")
                .addAppArgs(jsonPath)
                .setMainClass("org.dhruv.Main")
                .setMaster("spark://dhruv-VMware-Virtual-Platform:7077")
                .setAppName("will-be-chosen-from-extract");

        try {
            SparkAppHandle launcherHandle = sparkLauncher.startApplication();
            launcherHandle.addListener((Listener) new Listener() {

                @Override
                public void stateChanged(SparkAppHandle handle) {
                    String state = launcherHandle.getState().toString();
                    System.out.println("state changed to: " + state);
                    extractJobService.updateJobStatusById(extract, state);
                }

                @Override
                public void infoChanged(SparkAppHandle handle) {
                    System.out.println("Info changed: " + handle.getState());
                }

            });
        } catch (IOException e) {
            log.error("Application failed");
            e.printStackTrace();
        }

    }

    public void launchSampleSparkJob() {
        System.out.println("In spark Application Launcher");
        SparkLauncher sparkLauncher = new SparkLauncher()
                .setAppResource("/home/dhruv/projects/sample-spark-job/sparkjob/target/jobj.jar")
                .setConf("spark.driver.extraClassPath",
                        "/home/dhruv/Downloads/gson-2.10.jar:/opt/spark/jars/gson-2.2.4.jar")
                .addJar("/home/dhruv/Downloads/gson-2.10.jar")
                .addAppArgs("/home/dhruv/projects/sample-spark-job/data/test-json/test.json")
                .setMainClass("org.dhruv.Main")
                .setMaster("spark://dhruv-VMware-Virtual-Platform:7077")
                .setAppName("will-be-chosen-from-extract");

        try {
            SparkAppHandle launcherHandle = sparkLauncher.startApplication();
            launcherHandle.addListener((Listener) new Listener() {

                @Override
                public void stateChanged(SparkAppHandle handle) {
                    System.out.println("state changed to: " + launcherHandle.getState());
                }

                @Override
                public void infoChanged(SparkAppHandle handle) {
                    System.out.println("Info changed: " + handle.getState());
                }

            });
        } catch (IOException e) {
            log.error("Application failed");
            e.printStackTrace();
        }
    }
}
