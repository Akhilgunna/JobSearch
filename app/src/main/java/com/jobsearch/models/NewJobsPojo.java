package com.jobsearch.models;

import com.google.gson.annotations.SerializedName;

public class NewJobsPojo {

    @SerializedName("compeny_name")
    private String compeny_name;

    @SerializedName("description")
    private String description;

    @SerializedName("locoation")
    private String locoation;

    @SerializedName("salary")
    private String salary;

    @SerializedName("jobtype")
    private String jobtype;

    @SerializedName("photo")
    private String photo;



    public NewJobsPojo(String compeny_name, String description, String locoation, String salary, String jobtype, String photo){

        this.setCompeny_name(compeny_name);
        this.setDescription(description);
        this.setLocoation(locoation);
        this.setSalary(salary);
        this.setJobtype(jobtype);
        this.setPhoto(photo);


    }


    public String getCompeny_name() {
        return compeny_name;
    }

    public void setCompeny_name(String compeny_name) {
        this.compeny_name = compeny_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocoation() {
        return locoation;
    }

    public void setLocoation(String locoation) {
        this.locoation = locoation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}