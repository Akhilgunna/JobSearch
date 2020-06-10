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
import com.jobsearch.adapters.CheckAppliedJobsAdapter;
import com.jobsearch.adapters.ListOfJobsAdapter;
import com.jobsearch.models.CheckeAppliedJobsPojo;
import com.jobsearch.models.ListOfJobsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckAppliedJobsActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<CheckeAppliedJobsPojo> al;
    SharedPreferences sharedPreferences;
    String uname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_applied_jobs);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("user_name", "");

        getSupportActionBar().setTitle("List Of Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        al= new ArrayList<>();
//        al.add(new ListOfJobsPojo("Apple","40000","Full Time","ABC","Xyz"));
//        al.add(new ListOfJobsPojo("Apple","40000","Full Time","ABC","Xyz"));
//        al.add(new ListOfJobsPojo("Apple","40000","Full Time","ABC","Xyz"));
//        al.add(new ListOfJobsPojo("Apple","40000","Full Time","ABC","Xyz"));
//        al.add(new ListOfJobsPojo("Apple","40000","Full Time","ABC","Xyz"));

       // list_view.setAdapter(new ListOfJobsAdapter(al, ListOfJobsActivity.this));
        serverData();
    }
    public void serverData(){
        progressDialog = new ProgressDialog(CheckAppliedJobsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("user_name", "");
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<CheckeAppliedJobsPojo>> call = service.check_applied_jobs(uname);
        call.enqueue(new Callback<List<CheckeAppliedJobsPojo>>() {
            @Override
            public void onResponse(Call<List<CheckeAppliedJobsPojo>> call, Response<List<CheckeAppliedJobsPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(CheckAppliedJobsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    al = response.body();
                    list_view.setAdapter(new CheckAppliedJobsAdapter(al, CheckAppliedJobsActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<CheckeAppliedJobsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CheckAppliedJobsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
    /*public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idlogout:
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();

            case android.R.id.home:
                this.finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}

