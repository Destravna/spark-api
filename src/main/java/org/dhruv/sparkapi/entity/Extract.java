package org.dhruv.sparkapi.entity;

import jakarta.persistence.*;
// import jakarta.persistence.Table;


//submitted extracts 
@Entity 
@Table(name = "extract")
public class Extract {
    
    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(name = "extract_id")
    private Long extractId;

    @Column(name = "extract_name", nullable = false)
    private String extractName;

    @Column(name = "extract_status", nullable = false)
    private String extractStatus;

    public Extract(){

    }

    public Extract(Extracts extracts){
        this.extractId = extracts.getExtractId();
        this.extractName = extracts.getExtractName();
        this.extractStatus = "submitted";
    }

    public Long getJobId(){
        return jobId;
    }

    public void setJobId(Long jobId){
        this.jobId = jobId;
    }

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

    public String getExtractStatus(){
        return extractStatus;
    }

    public void setExtractStatus(String status){
        this.extractStatus = status;
    }

    

    @Override
    public String toString() {
        return "id : "  + extractId + ", name :  " + extractName + ", json : " + extractStatus;
    }



}
