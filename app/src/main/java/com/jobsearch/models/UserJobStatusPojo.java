package com.jobsearch.models;

import com.google.gson.annotations.SerializedName;

public class UserJobStatusPojo {
    @SerializedName("Id")
    private String Id;

    @SerializedName("job_title")
    private String job_title;

    @SerializedName("status")
    private String status;



    public UserJobStatusPojo(String Id, String job_title, String status) {

    this.setJob_title(job_title);
    this.setStatus(status);
     this.setId(Id);

    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
