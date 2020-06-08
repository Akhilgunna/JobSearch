package com.jobsearch.models;

import com.google.gson.annotations.SerializedName;

public class ListOfUserJobsPojo {
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

    @SerializedName("title")
    private String title;

    @SerializedName("qualification")
    private String qualification ;

    @SerializedName("img_photo")
    private String img_photo;

    public ListOfUserJobsPojo(String Id, String c_name, String salary, String work_type, String location, String about,String title,String qualification,String img_photo) {

     this.setSalary(salary);
     this.setWork_type(work_type);
     this.setC_name(c_name);
     this.setLocation(location);
     this.setAbout(about);
     this.setId(Id);
     this.setTitle(title);
     this.setQualification(qualification);
     this.setImg_photo(img_photo);

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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getImg_photo() {
        return img_photo;
    }

    public void setImg_photo(String img_photo) {
        this.img_photo = img_photo;
    }
}
