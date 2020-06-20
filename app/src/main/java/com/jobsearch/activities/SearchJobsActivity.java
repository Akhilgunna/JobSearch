package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.Utils;
import com.jobsearch.adapters.ListOfUserJobsAdapter;
import com.jobsearch.adapters.SearchJobsAdapter;
import com.jobsearch.models.ListOfUserJobsPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchJobsActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<ListOfUserJobsPojo> al;
    SearchJobsAdapter searchJobsAdapter;
    String etsearch,etlocation,etsalary,etjob_type;
    TextView tv_nojobs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_jobs);

        getSupportActionBar().setTitle("Search Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_nojobs=(TextView)findViewById(R.id.tv_nojobs);

        list_view=(ListView)findViewById(R.id.list_view);
        etsearch=getIntent().getStringExtra("search");
        etlocation=getIntent().getStringExtra("location");
        etjob_type=getIntent().getStringExtra("job_type");
        etsalary=getIntent().getStringExtra("salary");


        al= new ArrayList<>();

        serverData();
    }
    public void serverData(){
        progressDialog = new ProgressDialog(SearchJobsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<ListOfUserJobsPojo>> call = service.searchFilter(etsearch,etlocation,etjob_type,etsalary);
        call.enqueue(new Callback<List<ListOfUserJobsPojo>>() {
            @Override
            public void onResponse(Call<List<ListOfUserJobsPojo>> call, Response<List<ListOfUserJobsPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(SearchJobsActivity.this,"No data found",Toast.LENGTH_SHORT).show();

                }else {
                    al = response.body();

                    list_view.setAdapter(new SearchJobsAdapter(al, SearchJobsActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<ListOfUserJobsPojo>> call, Throwable t) {
                progressDialog.dismiss();
               // Toast.makeText(SearchJobsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Toast.makeText(SearchJobsActivity.this,"Oops No Jobs found...!",Toast.LENGTH_SHORT).show();
                tv_nojobs.setText("Oops No Jobs found...!");
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

