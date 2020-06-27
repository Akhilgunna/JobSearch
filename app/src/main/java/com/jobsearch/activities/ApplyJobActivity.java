package com.jobsearch.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.ResponseData;
import com.jobsearch.RetrofitInstance;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyJobActivity extends AppCompatActivity {
    TextView tv_dob,tv_time;
    int mYear,mMonth,mDay;
    String DAY,MONTH,YEAR;
    Button bt_confirm_appont,bt_check_aval;
    ImageView image_view;
    TextView tv_about,tView;
    Button bt_apply_now,bt_upload_resume;
    RadioButton radio_yes,radio_no;
    LinearLayout ll;
    ProgressBar progressBar;
    String img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        getSupportActionBar().setTitle("Job Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img=getIntent().getStringExtra("img_logo");

        image_view=(ImageView)findViewById(R.id.image_view);
        Glide.with(ApplyJobActivity.this).load(getIntent().getStringExtra("img_logo")).into(image_view);

        ll=(LinearLayout)findViewById(R.id.ll);

        tv_about=(TextView)findViewById(R.id.tv_about);
        tv_about.setText(getIntent().getStringExtra("about"));

        radio_yes=(RadioButton)findViewById(R.id.radio_yes);
        radio_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll.setVisibility(View.VISIBLE);
            }
        });
        radio_no=(RadioButton)findViewById(R.id.radio_no);
        radio_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ApplyJobActivity.this, "Due to Covid 19 we are Conducting Online Interviews only", Toast.LENGTH_SHORT).show();
                ll.setVisibility(View.GONE);
            }
        });

        bt_upload_resume=(Button)findViewById(R.id.bt_upload_resume);
        bt_confirm_appont=(Button)findViewById(R.id.bt_confirm_appont);
        bt_confirm_appont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ApplyJobActivity.this,FillTheDetailsFormActivity.class);
                intent.putExtra("date",tv_dob.getText().toString());
                intent.putExtra("time",tv_time.getText().toString());
                intent.putExtra("id",getIntent().getStringExtra("id"));
                intent.putExtra("title",getIntent().getStringExtra("title"));
                intent.putExtra("logo",img);

                startActivity(intent);

            }
        });

        tv_dob=(TextView)findViewById(R.id.tv_dob);
        tv_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepicker();

            }
        });
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmTimePicker();


            }
        });
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        bt_check_aval=(Button)findViewById(R.id.bt_check_aval);
        bt_check_aval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("availability").equals("Yes")) {
                    Toast.makeText(ApplyJobActivity.this, "The Selected Schedule is Avalilable", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ApplyJobActivity.this, "The Following  Schedule is not Avalilable", Toast.LENGTH_SHORT).show();
                }


            }
        });
        tView=(TextView)findViewById(R.id.tView);

    }
    /*ProgressDialog progressDialog;
    private void submitData() {
        String time = tv_time.getText().toString();
        String date = tv_dob.getText().toString();


        progressDialog = new ProgressDialog(ApplyJobActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.apply_job(time,date);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().status.equals("true")) {
                    progressDialog.dismiss();
                    Toast.makeText(ApplyJobActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(ApplyJobActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ApplyJobActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }*/
    public void datepicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + "";
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";

                        tv_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void setmTimePicker(){

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ApplyJobActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                tv_time.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

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