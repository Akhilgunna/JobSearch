package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.ResponseData;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditListOfJobsActivity extends AppCompatActivity {
    EditText et_title,et_company_name,et_locoation,et_about,et_salary;
    RadioButton radio_parttime,radio_fulltime;
    Button bt_post_job;
    Spinner spin_salary,spin_qualification;
    SharedPreferences sharedPreferences;
    String session;
    String[] string_spin_sal,string_spin_qualif;
    ProgressDialog progressDialog;
    String worktype;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_of_jobs);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);

        session = sharedPreferences.getString("user_name", "def-val");

        getSupportActionBar().setTitle("Edit Job Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        string_spin_sal=EditListOfJobsActivity.this.getResources().getStringArray(R.array.spin_salary);
        //string_spin_qualif=EditListOfJobsActivity.this.getResources().getStringArray(R.array.spin_qualif);


        et_company_name=(EditText)findViewById(R.id.et_company_name);
        et_locoation=(EditText)findViewById(R.id.et_locoation);
        et_about=(EditText)findViewById(R.id.et_about);
        radio_parttime=(RadioButton)findViewById(R.id.radio_parttime);
        radio_fulltime=(RadioButton)findViewById(R.id.radio_fulltime);
        spin_salary=(Spinner)findViewById(R.id.spin_salary);
        //spin_qualification=(Spinner)findViewById(R.id.spin_qualification);

        Toast.makeText(EditListOfJobsActivity.this, getIntent().getStringExtra("id"), Toast.LENGTH_LONG).show();

        et_company_name.setText(getIntent().getStringExtra("cname"));
        et_locoation.setText(getIntent().getStringExtra("location"));
        et_about.setText(getIntent().getStringExtra("about"));

        int sal = new ArrayList<String>(Arrays.asList(string_spin_sal)).indexOf(getIntent().getStringExtra("salary"));
        spin_salary.setSelection(sal);

        /*int qualif = new ArrayList<String>(Arrays.asList(string_spin_qualif)).indexOf(getIntent().getStringExtra("salary"));
        spin_salary.setSelection(qualif);
*/
        if (getIntent().getStringExtra("work_type").equals("FullTime")) {  //Assign data to Radio Button
            //rg.check(radioMale.getId());
            radio_fulltime.setSelected(true);
        } else {
            //rg.check(radioFemale.getId());
            radio_parttime.setSelected(true);
        }
        bt_post_job=(Button)findViewById(R.id.bt_post_job);
        bt_post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();

            }
        });

    }
    private void submitData() {
        String c_name = et_company_name.getText().toString();
        String location = et_locoation.getText().toString();
        String about = et_about.getText().toString();
        String sal = spin_salary.getSelectedItem().toString();
        f2();


        progressDialog = new ProgressDialog(EditListOfJobsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.update_jobdetails(getIntent().getStringExtra("id"),c_name,location,about,sal,worktype);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().status.equals("true")) {
                    progressDialog.dismiss();
                    Toast.makeText(EditListOfJobsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditListOfJobsActivity.this, ListOfJobsActivity.class));


                } else {
                    Toast.makeText(EditListOfJobsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditListOfJobsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void f2() {

        if (radio_fulltime.isChecked()) {
            worktype = "FullTime";
        } else {
            worktype = "PartTime";
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
