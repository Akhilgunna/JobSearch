package com.jobsearch.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.ResponseData;
import com.jobsearch.RetrofitInstance;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
    EditText et_first_name,et_last_name,et_email,et_phone_no,et_password;
    Spinner spin_stud_emp;
    Button bt_reg;
    Button select_image;
    RadioButton radioMale,radioFemale;
    String str_gender;
    ProgressDialog progressDialog;
    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://freegreetingsadda.com/";
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();

        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f2();
                if (et_first_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "First Name Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_last_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Last Name Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_phone_no.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Phone Number Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (spin_stud_emp.getSelectedItem().toString().equals("Student/Employee")) {
                    Toast.makeText(getApplicationContext(), "Choose Student/Employee", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                submitData();

            }
        });
    }

    private void submitData() {
        String first_name = et_first_name.getText().toString();
        String last_name = et_last_name.getText().toString();
        String email = et_email.getText().toString();
        String phno = et_phone_no.getText().toString();
        String pwd = et_password.getText().toString();
        String stud_emp=spin_stud_emp.getSelectedItem().toString();


       progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

   EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.ur(first_name, phno, email,last_name,pwd,stud_emp,str_gender);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(RegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(RegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void f2() {

        if (radioMale.isChecked()) {
            str_gender = "M";
        } else {
            str_gender = "F";
        }
    }
    public void initViews(){
        radioFemale=(RadioButton)findViewById(R.id.radioFemale);
        radioMale=(RadioButton)findViewById(R.id.radioMale);
        et_first_name=(EditText)findViewById(R.id.et_first_name);
        et_last_name=(EditText)findViewById(R.id.et_last_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phone_no=(EditText)findViewById(R.id.et_phone_no);
        et_password=(EditText)findViewById(R.id.et_password);
        spin_stud_emp=(Spinner)findViewById(R.id.spin_stud_emp);

        bt_reg=(Button)findViewById(R.id.bt_reg);

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