package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.ResponseData;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button bt_login;
    TextView tv_reg_here,tv_forget_pass;
    Spinner spin_stud_emp;
    EditText et_uname,et_password;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("User Login");
       /* getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        spin_stud_emp=(Spinner)findViewById(R.id.spin_stud_emp);
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        tv_forget_pass=(TextView)findViewById(R.id.tv_forget_pass);
        tv_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        et_uname=(EditText)findViewById(R.id.et_uname);
        et_password=(EditText)findViewById(R.id.et_password);


        tv_reg_here=(TextView)findViewById(R.id.tv_reg_here);
        tv_reg_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);

            }
        });

        bt_login=(Button)findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*Intent intent=new Intent(LoginActivity.this, MyProfileActivity.class);
                startActivity(intent);*/

                if(et_uname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter your User Name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter your Password",Toast.LENGTH_LONG).show();
                    return;
                }
                submitData();

            }
        });
    }
    ProgressDialog progressDialog;
    private void submitData(){
        String str=et_uname.getText().toString();
        String str1=et_password.getText().toString();
        String str2=spin_stud_emp.getSelectedItem().toString();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.user_login(str,str1,str2);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body().status.equals("true")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_name",et_uname.getText().toString());
                    editor.commit();

                    if(response.body().message.equals("User Success")) {
                        startActivity(new Intent(LoginActivity.this, MyProfileActivity.class));
                        finish();
                    }

                    else if(response.body().message.equals("Emp Success")) {
                        startActivity(new Intent(LoginActivity.this, MyEmployerProfileActivity.class));
                        finish();
                    }
                    if(response.body().message.equals("Admin Success")) {
                       // startActivity(new Intent(LoginActivity.this, MyProfileActivity.class));
                       // finish();
                        Toast.makeText(LoginActivity.this,response.body().message,Toast.LENGTH_LONG).show();


                    }




                }else{
                    Toast.makeText(LoginActivity.this,response.body().message,Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}