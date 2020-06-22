package com.jobsearch.models;

import com.google.gson.annotations.SerializedName;

public class QualificationPojo {

    @SerializedName("name")
    private
    String name;

    public QualificationPojo(String name)
    {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }
}
