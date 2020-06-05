package com.jobsearch.activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.ResponseData;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.Utils;
import com.jobsearch.models.EditMyJobsPojo;
import com.jobsearch.models.EditProfilePojo;

import java.util.List;

public class EditJobActivity extends AppCompatActivity {
    EditText et_title,et_company_name,et_locoation,et_qulification_req,et_about,et_salary;
    RadioButton radio_parttime,radio_fulltime;
    Button bt_post_job,select_image;
    String worktype,session;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    List<EditMyJobsPojo> a1;
    ResponseData a2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);
        getSupportActionBar().setTitle("Update Job");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);

        session = sharedPreferences.getString("user_name", "def-val");

        et_title=(EditText)findViewById(R.id.et_title);
        et_company_name=(EditText)findViewById(R.id.et_company_name);
        et_locoation=(EditText)findViewById(R.id.et_locoation);
        et_qulification_req=(EditText)findViewById(R.id.et_qulification_req);
        et_about=(EditText)findViewById(R.id.et_about);
        et_salary=(EditText)findViewById(R.id.et_salary);
        radio_parttime=(RadioButton)findViewById(R.id.radio_parttime);
        radio_fulltime=(RadioButton)findViewById(R.id.radio_fulltime);


        progressDialog = new ProgressDialog(EditJobActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<EditMyJobsPojo>> call = service.EditMyJob(session);

        call.enqueue(new Callback<List<EditMyJobsPojo>>() {
            @Override
            public void onResponse(Call<List<EditMyJobsPojo>> call, Response<List<EditMyJobsPojo>> response) {

                progressDialog.dismiss();
                a1 = response.body();
                // Toast.makeText(getApplicationContext(),""+response.body().size(),Toast.LENGTH_LONG).show();
                EditMyJobsPojo user = a1.get(0);

                et_title.setText(user.getEt_title());

                et_company_name.setText(user.getC_name());


                et_locoation.setText(user.getLocation());

                et_qulification_req.setText(user.getQualification());
                et_about.setText(user.getAbout());

                et_salary.setText(user.getSalary());



            }

            @Override
            public void onFailure(Call<List<EditMyJobsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditJobActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void submitData() {
        String title = et_title.getText().toString();
        String cname = et_company_name.getText().toString();
        String locoation= et_locoation.getText().toString();
        String qualification = et_qulification_req.getText().toString();
        String about = et_about.getText().toString();
        String salaary = et_salary.getText().toString();


        progressDialog = new ProgressDialog(EditJobActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        session = sharedPreferences.getString("user_name", "def-val");
        Toast.makeText(EditJobActivity.this, session, Toast.LENGTH_SHORT).show();


        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.update_job(title,cname,locoation,qualification,about,salaary);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                progressDialog.dismiss();
                a2 = response.body();

                if (response.body().status.equals("true")) {
                    Toast.makeText(EditJobActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(EditJobActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditJobActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
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