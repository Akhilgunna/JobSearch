package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.Utils;
import com.jobsearch.adapters.ListOfUserJobsAdapter;
import com.jobsearch.adapters.NewUserJobsAdapter;
import com.jobsearch.models.ListOfUserJobsPojo;
import com.jobsearch.models.NewUserJobsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewUserJobsActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<NewUserJobsPojo> al;
    SharedPreferences sharedPreferences;
    ListOfUserJobsAdapter listOfUserJobsAdapter;
    ImageView iv;
    Spinner spin_order,$order;
    String m;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_jobs);


        getSupportActionBar().setTitle("New Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv=(ImageView)findViewById(R.id.iv);
        String gifUrl = "https://1.bp.blogspot.com/-7F5ZtDVy-C8/Xaq4p1MLPlI/AAAAAAAAAeI/7EWe3n4cc4wDt8F-GrV6B_CcP-7QxIlgACLcBGAsYHQ/s1600/arrow-with-new-letter-icon-gif-animation%2B%25281%2529.gif";
        https://singaporemotherhood.com/forum/attachments/new-gif.695095/
        Glide.with(NewUserJobsActivity.this ).load( gifUrl ).into(iv);

        spin_order=(Spinner)findViewById(R.id.spin_order);
        spin_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(spin_order.getSelectedItem().toString().equals("Latest - Old"))
                {
                    m="new";
                    serverData(m);
                    //Toast.makeText(NewUserJobsActivity.this, m, Toast.LENGTH_SHORT).show();
                }
                else {
                    m="old";
                    serverData(m);
                    //Toast.makeText(NewUserJobsActivity.this, m, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        list_view=(ListView)findViewById(R.id.list_view);
        al= new ArrayList<>();


    }
    public void serverData(final String mode){
        progressDialog = new ProgressDialog(NewUserJobsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<NewUserJobsPojo>> call = service.newuserJobs(mode);
        call.enqueue(new Callback<List<NewUserJobsPojo>>() {
            @Override
            public void onResponse(Call<List<NewUserJobsPojo>> call, Response<List<NewUserJobsPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(NewUserJobsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    al = response.body();
                    list_view.setAdapter(new NewUserJobsAdapter(al, NewUserJobsActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<NewUserJobsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NewUserJobsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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

