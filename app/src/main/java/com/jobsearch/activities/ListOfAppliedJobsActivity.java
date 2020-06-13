package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.adapters.ListOfAppliedJobsAdapter;
import com.jobsearch.models.ListOfAppliedJobsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfAppliedJobsActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<ListOfAppliedJobsPojo> a1;
    String str_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_applied_jobs);

        getSupportActionBar().setTitle("Applied Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        str_id=getIntent().getStringExtra("id");
        //Toast.makeText(ListOfAppliedJobsActivity.this,"GET ID"+str_id,Toast.LENGTH_LONG).show();
        list_view=(ListView)findViewById(R.id.list_view);
        a1= new ArrayList<>();
        /*a1.add(new ListOfAppliedJobsPojo("Parameswar Reddy","23","3 Years","testing@gmail.com","Xyz","516321","Uploaded","09_06_2020","9:00 AM")); //static data
        a1.add(new ListOfAppliedJobsPojo("Parameswar Reddy","23","3 Years","testing@gmail.com","Xyz","516321","Uploaded","09_06_2020","9:00 AM")); //static data
        a1.add(new ListOfAppliedJobsPojo("Parameswar Reddy","23","3 Years","testing@gmail.com","Xyz","516321","Uploaded","09_06_2020","9:00 AM")); //static data
        a1.add(new ListOfAppliedJobsPojo("Parameswar Reddy","23","3 Years","testing@gmail.com","Xyz","516321","Uploaded","09_06_2020","9:00 AM")); //static data
        a1.add(new ListOfAppliedJobsPojo("Parameswar Reddy","23","3 Years","testing@gmail.com","Xyz","516321","Uploaded","09_06_2020","9:00 AM")); //static data
        a1.add(new ListOfAppliedJobsPojo("Parameswar Reddy","23","3 Years","testing@gmail.com","Xyz","516321","Uploaded","09_06_2020","9:00 AM")); //static data*/

        //list_view.setAdapter(new ListOfAppliedJobsAdapter(a1, ListOfAppliedJobsActivity.this));
        serverData();
    }
    public void serverData(){
        progressDialog = new ProgressDialog(ListOfAppliedJobsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<ListOfAppliedJobsPojo>> call = service.listOfAppliedJobs(str_id);
        call.enqueue(new Callback<List<ListOfAppliedJobsPojo>>() {
            @Override
            public void onResponse(Call<List<ListOfAppliedJobsPojo>> call, Response<List<ListOfAppliedJobsPojo>> response) {
                //Toast.makeText(AvalableRoomsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(ListOfAppliedJobsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    a1 = response.body();
                    // Toast.makeText(MyAddsActivity.this,"ddddd   "+response.body(),Toast.LENGTH_SHORT).show();
                    list_view.setAdapter(new ListOfAppliedJobsAdapter(a1, ListOfAppliedJobsActivity.this));


                }
            }

            @Override
            public void onFailure(Call<List<ListOfAppliedJobsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ListOfAppliedJobsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
