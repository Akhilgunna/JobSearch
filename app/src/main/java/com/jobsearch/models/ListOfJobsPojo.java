package com.jobsearch.models;

import com.google.gson.annotations.SerializedName;

public class ListOfJobsPojo {
    @SerializedName("Id")
    private String Id;

    @SerializedName("c_name")
    private String c_name;

    @SerializedName("salary")
    private String salary;

    @SerializedName("work_type")
    private String work_type;


    @SerializedName("location")
    private String location;

    @SerializedName("about")
    private String about;

    public ListOfJobsPojo(String Id,String c_name, String salary, String work_type,String location,String about) {

     this.setSalary(salary);
     this.setWork_type(work_type);
     this.setC_name(c_name);
     this.setLocation(location);
     this.setAbout(about);
     this.setId(Id);

    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
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


}
