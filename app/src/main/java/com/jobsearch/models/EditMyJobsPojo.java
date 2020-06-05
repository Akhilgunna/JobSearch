package com.jobsearch.models;

import com.google.gson.annotations.SerializedName;

public class EditMyJobsPojo {

    @SerializedName("c_name")
    private String c_name;

    @SerializedName("et_title")
    private String et_title;

    @SerializedName("salary")
    private String salary;

    @SerializedName("qualification")
    private String qualification;

    @SerializedName("work_type")
    private String work_type;


    @SerializedName("location")
    private String location;



    @SerializedName("about")
    private String about;



    public EditMyJobsPojo(String c_name, String salary,String qualification, String work_type,String location,String about,String et_title) {

        this.setSalary(salary);
        this.setWork_type(work_type);
        this.setC_name(c_name);
        this.setLocation(location);
        this.setAbout(about);
        this.setEt_title(et_title);
        this.setQualification(qualification);

    }



    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }


    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEt_title() {
        return et_title;
    }

    public void setEt_title(String et_title) {
        this.et_title = et_title;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}