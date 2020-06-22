package com.jobsearch.models;

import com.google.gson.annotations.SerializedName;

public class ListOfAppliedJobsPojo {

    @SerializedName("Id")
    private String Id;

    @SerializedName("job_title")
    private String job_title;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private String age;

    @SerializedName("exp")
    private String exp;

    @SerializedName("email")
    private String email;

    @SerializedName("location")
    private String location;

    @SerializedName("postal")
    private String postal ;

    @SerializedName("resume")
    private String resume;

    @SerializedName("dat")
    private String dat;

    @SerializedName("time")
    private String time;


    public ListOfAppliedJobsPojo(String Id,String job_title,String name, String age, String exp, String email, String location, String postal, String resume, String dat, String time){
        this.job_title=job_title;
        this.setName(name);
        this.setAge(age);
        this.setExp(exp);
        this.setEmail(email);
        this.setLocation(location);
        this.setPostal(postal);
        this.setResume(resume);
        this.setDat(dat);
        this.setTime(time);
        this.setId(Id);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
