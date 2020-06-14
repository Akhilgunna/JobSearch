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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.ResponseData;

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

public class FillTheDetailsFormActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    Button btn_apply_now,bt_upload_resume;
    ProgressDialog progressDialog;
    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://parttimejobs.site/";
    private Uri uri;
    EditText et_name,et_age,et_experience,et_emailid,et_location,et_postal_code;
    TextView tv_Location;
    Button bt_picker;
    int PLACE_PICKER_REQUEST =1;
    StringBuilder stringBuilder;
    Spinner spin_experience,spinner_age;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillthe_details_form);

        getSupportActionBar().setTitle("Fill The Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spin_experience=(Spinner)findViewById(R.id.spin_experience);
        spinner_age=(Spinner)findViewById(R.id.spinner_age);
        //tv_Location=(TextView)findViewById(R.id.tv_Location);
        bt_picker=(Button)findViewById(R.id.bt_picker);
        bt_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(FillTheDetailsFormActivity.this)
                            ,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        et_name=(EditText)findViewById(R.id.et_name);
       // et_age=(EditText)findViewById(R.id.et_age);
       // et_experience=(EditText)findViewById(R.id.et_experience);
        et_emailid=(EditText)findViewById(R.id.et_emailid);
        et_location=(EditText)findViewById(R.id.et_location);
        et_postal_code=(EditText)findViewById(R.id.et_postal_code);

        bt_upload_resume=(Button)findViewById(R.id.bt_upload_resume);
        bt_upload_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });

        btn_apply_now=(Button)findViewById(R.id.btn_apply_now);
        btn_apply_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(FillTheDetailsFormActivity.this, "Job Applied Successfully", Toast.LENGTH_SHORT).show();
                uploadImageToServer();

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, FillTheDetailsFormActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, FillTheDetailsFormActivity.this);
                file = new File(filePath);

            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if(requestCode==PLACE_PICKER_REQUEST){
                if(requestCode==RESULT_OK);{
                    Place place=PlacePicker.getPlace(data,this);
                    stringBuilder=new StringBuilder();
                    String latitude= String.valueOf(place.getLatLng().latitude);
                    String longitude= String.valueOf(place.getLatLng().longitude);

                    stringBuilder.append("LATITUDE :");
                    stringBuilder.append(latitude);

                    stringBuilder.append("\n");

                    stringBuilder.append("LONGITUDE :");
                    stringBuilder.append(longitude);

                    et_location.setText(stringBuilder.toString());
                }

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
            String filePath = getRealPathFromURIPath(uri, FillTheDetailsFormActivity.this);
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
        pd=new ProgressDialog(FillTheDetailsFormActivity.this);
        pd.setTitle("Loading");
        pd.show();
        Map<String, String> map = new HashMap<>();
        map.put("job_id",getIntent().getStringExtra("id"));
        map.put("job_title",getIntent().getStringExtra("title"));
        map.put("dat",getIntent().getStringExtra("date"));
        map.put("time",getIntent().getStringExtra("time"));
        map.put("name",et_name.getText().toString());
        //map.put("age",et_age.getText().toString());
       // map.put("exp",et_experience.getText().toString());
        map.put("age",spinner_age.getSelectedItem().toString());
        map.put("exp",spin_experience.getSelectedItem().toString());
        map.put("email", et_emailid.getText().toString());
        map.put("location",et_location.getText().toString());
        map.put("postal",et_postal_code.getText().toString());


        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EndPointUrl uploadImage = retrofit.create(EndPointUrl.class);
        Call<ResponseData> fileUpload = uploadImage.apply_job(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                Toast.makeText(FillTheDetailsFormActivity.this, "Job Applied Successfully.", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(FillTheDetailsFormActivity.this,MyProfileActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(FillTheDetailsFormActivity.this, "Error"+t.getMessage(), Toast.LENGTH_LONG).show();
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
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PLACE_PICKER_REQUEST){
            if(requestCode==RESULT_OK);{
                Place place=PlacePicker.getPlace(data,this);
                StringBuilder stringBuilder=new StringBuilder();
                String latitude= String.valueOf(place.getLatLng().latitude);
                String longitude= String.valueOf(place.getLatLng().longitude);

                stringBuilder.append("LATITUDE :");
                stringBuilder.append(latitude);

                stringBuilder.append("\n");

                stringBuilder.append("LONGITUDE :");
                stringBuilder.append(longitude);

                tv_Location.setText(stringBuilder.toString());
            }

        }
    }*/
}
