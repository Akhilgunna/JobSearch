package com.jobsearch.models;

import com.google.gson.annotations.SerializedName;

public class SalaryPojo {

    @SerializedName("salary")
    private
    String salary;

    public SalaryPojo(String salary)
    {
        this.setSalary(salary);
    }


    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
