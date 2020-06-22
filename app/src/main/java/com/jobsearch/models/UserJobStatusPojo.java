package com.jobsearch.models;

import com.google.gson.annotations.SerializedName;

public class UserJobStatusPojo {
    @SerializedName("Id")
    private String Id;

    @SerializedName("job_id")
    private String job_id;

    @SerializedName("job_title")
    private String job_title;

    @SerializedName("status")
    private String status;


    @SerializedName("dat")
    private String dat;


    @SerializedName("time")
    private String time;


    @SerializedName("location")
    private String location;







    public UserJobStatusPojo(String Id,String job_id, String job_title, String status,String dat,String time,String location) {

    this.setJob_title(job_title);
    this.setStatus(status);
     this.setId(Id);
     this.setDat(dat);
     this.setTime(time);
     this.setLocation(location);
     this.setJob_id(job_id);

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

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
