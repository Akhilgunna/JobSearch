package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.adapters.UserJobStatusAdapter;
import com.jobsearch.adapters.UserJobStatusDetailsAdapter;
import com.jobsearch.models.UserJobStatusPojo;
import com.jobsearch.models.UserJobstatusDetailsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserJobStatusDetails extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<UserJobstatusDetailsPojo> al;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_job_status_details);

        getSupportActionBar().setTitle("Job Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        al= new ArrayList<>();
        serverData();


    }

    public void serverData(){
        progressDialog = new ProgressDialog(UserJobStatusDetails.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<UserJobstatusDetailsPojo>> call = service.getjobsbyid(getIntent().getStringExtra("id"));
        call.enqueue(new Callback<List<UserJobstatusDetailsPojo>>() {
            @Override
            public void onResponse(Call<List<UserJobstatusDetailsPojo>> call, Response<List<UserJobstatusDetailsPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(UserJobStatusDetails.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    al = response.body();
                    list_view.setAdapter(new UserJobStatusDetailsAdapter(al, UserJobStatusDetails.this));
                }
            }

            @Override
            public void onFailure(Call<List<UserJobstatusDetailsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserJobStatusDetails.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
