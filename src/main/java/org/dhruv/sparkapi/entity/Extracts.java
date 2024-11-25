package org.dhruv.sparkapi.entity;

import jakarta.persistence.*;
// import jakarta.persistence.Table;

@Entity 
@Table(name = "extracts")
public class Extracts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extract_id")
    private Long extractId;

    @Column(name = "extract_name", nullable = false)
    private String extractName;

    @Column(name = "extract_json", columnDefinition = "JSON", nullable = false)
    private String extractJson;


    public Long getExtractId() {
        return extractId;
    }

    public void setExtractId(Long extractId) {
        this.extractId = extractId;
    }

    public String getExtractName() {
        return extractName;
    }

    public void setExtractName(String extractName) {
        this.extractName = extractName;
    }

    public String getExtractJson() {
        return extractJson;
    }

    public void setExtractJson(String extractJson) {
        this.extractJson = extractJson;
    }

    @Override
    public String toString() {
        return "id : "  + extractId + ", name :  " + extractName + ", json : " + extractJson;
    }



}
