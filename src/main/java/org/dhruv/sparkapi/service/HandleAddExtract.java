package org.dhruv.sparkapi.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dhruv.sparkapi.entity.Extract;
import org.dhruv.sparkapi.entity.Extracts;
import org.dhruv.sparkapi.repository.ExtractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class HandleAddExtract {
    private static final String fileStorageDir = "submitted-jsons";

    @Autowired
    private ExtractRepo extractRepo;
    @Autowired
    private ExtractJobService extractJobService;
    @Autowired
    private SparkJobLauncherService sparkJobLauncherService;

    public boolean startExtract(String jsonData) {
        System.out.println("________creating json file_____________");
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        if (jsonObject.has("extractName")) {
            String extractName = jsonObject.get("extractName").getAsString();
            System.out.println("extract name : " + extractName);
            String fileName = extractName;
            File jsonFile = new File(fileStorageDir + "/" + fileName);
            try {
                if (jsonFile.createNewFile()) {
                    String jsonFilePath = fileStorageDir + "/" + fileName;
                    System.out.println("file created : " + jsonFile.getName());
                    try (FileWriter fileWriter = new FileWriter(jsonFile)) {
                        fileWriter.write(jsonData);
                        System.out.println("JSON data has been written to " + fileName);
                        Extracts extracts = insertExtract(extractName, jsonData);
                        // job
                        Extract extractJob = extractJobService.insertJob(extracts);
                        sparkJobLauncherService.launchSparkJobWithGivenJson(jsonFilePath, extractJob);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;

            }
            return true;
        } else
            return false;

        // File file = new File("submitted-json/")
    }

    public Extracts insertExtract(String extractName, String jsonString) {
        Extracts extract = new Extracts();
        extract.setExtractName(extractName);
        extract.setExtractJson(jsonString);
        Extracts extractSaved = extractRepo.save(extract);
        return extractSaved;

    }
}
