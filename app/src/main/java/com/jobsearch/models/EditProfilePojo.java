package com.jobsearch.models;

import com.google.gson.annotations.SerializedName;

public class EditProfilePojo {
    @SerializedName("fname")
    private String fname ;

    @SerializedName("lname")
    private String lname ;

    @SerializedName("phone")
    private String phone ;

    @SerializedName("email")
    private String email ;

    @SerializedName("pwd")
    private String pwd ;

    EditProfilePojo(String fname,String lname, String phone, String email,String pwd){
        this.setFname(fname);
        this.setLname(lname);
        this.setPhone(phone);
        this.setEmail(email);
        this.setPwd(pwd);

    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
