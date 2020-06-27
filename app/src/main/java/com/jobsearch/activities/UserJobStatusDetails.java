package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.ResponseData;
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
    Button btn_delete;
    TextView tv_date,tv_time,tv_location,tv_status;
    CardView card_view;
    String jid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_job_status_details);

        getSupportActionBar().setTitle("Job Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_location=(TextView)findViewById(R.id.tv_location);

        tv_date.setText("Date:   "+getIntent().getStringExtra("date"));
        tv_time.setText("Time:   "+getIntent().getStringExtra("time"));
        tv_location.setText("Location:   "+getIntent().getStringExtra("locoation"));

        tv_status=(TextView)findViewById(R.id.tv_status);
        tv_status.setText("Status:   "+getIntent().getStringExtra("status"));

        jid=getIntent().getStringExtra("jid");
        card_view=(CardView)findViewById(R.id.card_view);
        if(getIntent().getStringExtra("status").equals("Selected"))
        {
            card_view.setVisibility(View.VISIBLE);
        }
        else {
            card_view.setVisibility(View.GONE);
        }

        btn_delete=(Button)findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverdelete(getIntent().getStringExtra("aid"));
            }
        });

        list_view=(ListView)findViewById(R.id.list_view);
        al= new ArrayList<>();
        serverData();


    }

    public void serverData(){
        progressDialog = new ProgressDialog(UserJobStatusDetails.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<UserJobstatusDetailsPojo>> call = service.getjobsbyid(getIntent().getStringExtra("jid"));
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

    public void serverdelete(String id){
        progressDialog = new ProgressDialog(UserJobStatusDetails.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.deletemsg(id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(UserJobStatusDetails.this,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(UserJobStatusDetails.this, MyProfileActivity.class);
                    UserJobStatusDetails.this.startActivity(intent);
                    // Toast.makeText(UserJobStatusDetails.this,"Status updated successfully",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
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
