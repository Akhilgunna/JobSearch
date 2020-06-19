package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.Utils;
import com.jobsearch.adapters.ListOfJobsAdapter;
import com.jobsearch.adapters.UserJobStatusAdapter;
import com.jobsearch.models.ListOfJobsPojo;
import com.jobsearch.models.UserJobStatusPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserJobStatusActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<UserJobStatusPojo> al;
    SharedPreferences sharedPreferences;
    String uname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_job_status);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("user_name", "");


        getSupportActionBar().setTitle("Applied Jobs Status");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        al= new ArrayList<>();

        serverData();
    }
    public void serverData(){
        progressDialog = new ProgressDialog(UserJobStatusActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<UserJobStatusPojo>> call = service.myAppliedJobsStatus(uname);
        call.enqueue(new Callback<List<UserJobStatusPojo>>() {
            @Override
            public void onResponse(Call<List<UserJobStatusPojo>> call, Response<List<UserJobStatusPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(UserJobStatusActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    al = response.body();
                    list_view.setAdapter(new UserJobStatusAdapter(al, UserJobStatusActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<UserJobStatusPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserJobStatusActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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

