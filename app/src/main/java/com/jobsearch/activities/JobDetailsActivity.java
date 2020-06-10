package com.jobsearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jobsearch.R;

public class JobDetailsActivity extends AppCompatActivity {
    ImageView image_view;
    Button bt_apply_now;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdetails);

        getSupportActionBar().setTitle("Job Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image_view=(ImageView)findViewById(R.id.image_view);
        Glide.with(JobDetailsActivity.this).load(getIntent().getStringExtra("img_logo")).into(image_view);

        bt_apply_now=(Button)findViewById(R.id.bt_apply_now);
        bt_apply_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(JobDetailsActivity.this,ScheduleAppointmentActivity.class);
                startActivity(intent);
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