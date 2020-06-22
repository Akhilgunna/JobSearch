package com.jobsearch.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.ResponseData;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.Utils;
import com.jobsearch.models.QualificationPojo;
import com.jobsearch.models.SalaryPojo;

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

public class PostaJobActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    EditText et_title,et_company_name,et_locoation,et_qulification_req,et_about,et_salary;
    RadioButton radio_parttime,radio_fulltime;
    Button bt_post_job,select_image;
    Spinner spin_salary,spin_qualification;
    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://parttimejobs.site/";
    private Uri uri;
    String worktype,session;
    SharedPreferences sharedPreferences;
    List<QualificationPojo> a3;
    List<SalaryPojo> a1;
    String[] qualificationstatus,getsal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_a_job);
        getQualification();
        getSalary();
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        getSupportActionBar().setTitle("Post a Job");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_title=(EditText)findViewById(R.id.et_title);
        et_company_name=(EditText)findViewById(R.id.et_company_name);
        et_locoation=(EditText)findViewById(R.id.et_locoation);
        //et_qulification_req=(EditText)findViewById(R.id.et_qulification_req);
        et_about=(EditText)findViewById(R.id.et_about);
        //et_salary=(EditText)findViewById(R.id.et_salary);
        radio_parttime=(RadioButton)findViewById(R.id.radio_parttime);
        radio_fulltime=(RadioButton)findViewById(R.id.radio_fulltime);

        spin_salary=(Spinner)findViewById(R.id.spin_salary);
        spin_qualification=(Spinner)findViewById(R.id.spin_qualification);

        select_image=(Button)findViewById(R.id.select_image);
        bt_post_job=(Button)findViewById(R.id.bt_post_job);
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);


            }
        });

        bt_post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent=new Intent(PostaJobActivity.this,ListOfJobsActivity.class);
               // startActivity(intent);
                f2();
                uploadImageToServer();

            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, PostaJobActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, PostaJobActivity.this);
                file = new File(filePath);

            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    File file;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, PostaJobActivity.this);
            file = new File(filePath);
            // uploadImageToServer();
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");

    }
    ProgressDialog pd;
    private void uploadImageToServer(){
        pd=new ProgressDialog(PostaJobActivity.this);
        pd.setTitle("Loading");
        pd.show();
        Map<String, String> map = new HashMap<>();
        map.put("title",et_title.getText().toString());
        map.put("company_name",et_company_name.getText().toString());
        map.put("locoation",et_locoation.getText().toString());
        map.put("qualification",spin_qualification.getSelectedItem().toString());
        map.put("about", et_about.getText().toString());
        map.put("salary",spin_salary.getSelectedItem().toString());
        map.put("work_type",worktype);
        map.put("post_by",session);


        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EndPointUrl uploadImage = retrofit.create(EndPointUrl.class);
        Call<ResponseData> fileUpload = uploadImage.post_job(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                Toast.makeText(PostaJobActivity.this, "Added successfully. ", Toast.LENGTH_LONG).show();
                finish();
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(PostaJobActivity.this, "Error"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void f2() {

        if (radio_fulltime.isChecked()) {
            worktype = "FullTime";
        } else {
            worktype = "PartTime";
        }
    }


    public void getQualification() {
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<QualificationPojo>> call = apiService.getqualification();
        call.enqueue(new Callback<List<QualificationPojo>>() {
            @Override
            public void onResponse(Call<List<QualificationPojo>> call, Response<List<QualificationPojo>> response) {
                if (response.body() != null) {
                    if(response.body().size()>0){
                        a3 = response.body();
                        qualificationstatus = new String[a3.size() + 1];
                        qualificationstatus[0] = "Select Qualification";
                        for (int i = 0; i < a3.size(); i++) {
                            qualificationstatus[i + 1] = a3.get(i).getName();
                        }
                        ArrayAdapter aa = new ArrayAdapter(PostaJobActivity.this, android.R.layout.simple_spinner_item, qualificationstatus);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spin_qualification.setAdapter(aa);
                        spin_qualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                                if (pos > 0) {

                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<List<QualificationPojo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }


    private void getSalary() {
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<SalaryPojo>> call = apiService.getsalary();
        call.enqueue(new Callback<List<SalaryPojo>>() {
            @Override
            public void onResponse(Call<List<SalaryPojo>> call, Response<List<SalaryPojo>> response) {
                if (response.body() != null) {
                    if(response.body().size()>0) {
                        a1 = response.body();
                        Log.d("TAG", "Response = " + a1);
                        getsal = new String[a1.size() + 1];
                        getsal[0] = "Select Salary";
                        for (int i = 0; i < a1.size(); i++) {
                            getsal[i + 1] = a1.get(i).getSalary();
                        }
                        ArrayAdapter aa = new ArrayAdapter(PostaJobActivity.this, android.R.layout.simple_spinner_item, getsal);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spin_salary.setAdapter(aa);
                        spin_salary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                                if (pos > 0) {


                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }}

            }

            @Override
            public void onFailure(Call<List<SalaryPojo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
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
