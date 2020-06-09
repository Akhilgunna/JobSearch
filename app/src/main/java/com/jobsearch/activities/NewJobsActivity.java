package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jobsearch.R;
import com.jobsearch.adapters.NewJobsAdapter;
import com.jobsearch.models.NewJobsPojo;

import java.util.ArrayList;
import java.util.List;

public class NewJobsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    NewJobsAdapter newJobsAdapter;
    List<NewJobsPojo> a1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newjobs);

        getSupportActionBar().setTitle("New Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);recyclerView.setLayoutManager(linearLayoutManager);

        a1= new ArrayList<>();
        a1.add(new NewJobsPojo("MicroSoft","Software","XYZ","42000","PHP Developer","full time","http://www.pngplay.com/wp-content/uploads/1/Microsoft-Logo-Transparent-File.png"));
        /*a1.add(new NewJobsPojo("IBM","Softwere","XYZ","52000","Android Development","https://www.freepnglogos.com/uploads/ibm-logo-png/ibm-logo-png-transparent-svg-vector-bie-supply-3.png"));
        a1.add(new NewJobsPojo("Apple","Softwere","XYZ","92000","IOS Development","https://cdn3.iconfinder.com/data/icons/picons-social/57/56-apple-512.png"));
        a1.add(new NewJobsPojo("CapGemini","BPO","XYZ","18000","Bpo-Google Maps","https://www.digitalsme.eu/digital/uploads/Cap-Gem-lead-image.jpg"));
        a1.add(new NewJobsPojo("Accenture","BPO YTTNS","XYZ","12000","Bpo","https://ali-wa.net/wp-content/uploads/2015/10/Accenture-Logo.jpg"));

*/
        newJobsAdapter=new NewJobsAdapter(NewJobsActivity.this,a1);
        recyclerView.setAdapter(newJobsAdapter);

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