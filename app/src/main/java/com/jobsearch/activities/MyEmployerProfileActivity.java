package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.Utils;
import com.jobsearch.adapters.MyProfileAdapter;
import com.jobsearch.models.EditProfilePojo;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class MyEmployerProfileActivity extends AppCompatActivity {
    CircularImageView image_view;
    EditText et_abt_company;
    TextView tv_fname,tv_email,tv_lname,tv_phone,cd_tv_name,cd_tv_email;
    EditText et_bio;
    Button bt_login,bt_resume,btn_post;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    List<EditProfilePojo> a1;
    CardView card_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_employeer_profile);

        card_view=(CardView)findViewById(R.id.card_view);
        card_view.setBackgroundResource(R.drawable.card_view_bg);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);

        session = sharedPreferences.getString("user_name", "def-val");

        navigationView();
        ActionBar mActionBar=getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);

        image_view=(CircularImageView)findViewById(R.id.image_view);
        tv_fname=(TextView)findViewById(R.id.tv_fname);
        tv_email=(TextView)findViewById(R.id.tv_email);
        tv_lname=(TextView)findViewById(R.id.tv_lname);
        tv_phone=(TextView)findViewById(R.id.tv_phone);

        cd_tv_name=(TextView)findViewById(R.id.cd_tv_name);
        cd_tv_email=(TextView)findViewById(R.id.cd_tv_email);


        btn_post=(Button)findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), PostaJobActivity.class);
                startActivity(i);
            }
        });

        progressDialog = new ProgressDialog(MyEmployerProfileActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<EditProfilePojo>> call = service.getMyProfile(session);

        call.enqueue(new Callback<List<EditProfilePojo>>() {
            @Override
            public void onResponse(Call<List<EditProfilePojo>> call, Response<List<EditProfilePojo>> response) {

                progressDialog.dismiss();
                a1 = response.body();

                EditProfilePojo user = a1.get(0);

                tv_fname.setText("  "+user.getFname());
                tv_lname.setText("  "+user.getLname());
                cd_tv_name.setText(user.getFname()+" "+user.getLname());
                cd_tv_email.setText(session);
                Glide.with(MyEmployerProfileActivity.this).load("http://parttimejobs.site/Jobsearch/"+user.getImg_photo()).into(image_view);
                tv_phone.setText("  "+user.getPhone());
                tv_email.setText("  "+session);

            }

            @Override
            public void onFailure(Call<List<EditProfilePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MyEmployerProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void navigationView(){
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.myprofile:
                        Intent intent=new Intent(getApplicationContext(), MyEmployerProfileActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.view_jobs:
                        Intent list=new Intent(getApplicationContext(), ListOfJobsActivity.class);
                        startActivity(list);
                        break;

                    case R.id.new_jobs:
                        Intent post=new Intent(getApplicationContext(), PostaJobActivity.class);
                        startActivity(post);
                        break;

                    case R.id.applied_jobs:
                        Intent applied_jobs=new Intent(getApplicationContext(), CheckAppliedJobsActivity.class);
                        startActivity(applied_jobs);
                        break;



                    case R.id.logout:
                        Intent logout=new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logout);
                        break;

                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /* @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();
         if (dl.isDrawerOpen(GravityCompat.START)) {
             dl.closeDrawer(GravityCompat.START);
         } else {
             dl.openDrawer(GravityCompat.START);
         }
         return super.onOptionsItemSelected(item);
     }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {

            dl.closeDrawer(GravityCompat.START);
        }
        else {

            dl.openDrawer(GravityCompat.START);
        }
        switch (id){
            case R.id.edit_profile:
                Intent contact = new Intent(getApplicationContext(), EditYourProfileActivity.class);
                startActivity(contact);
                return true;
            case R.id.change_pwd:
                Intent pwd = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(pwd);
                return true;
            case R.id.logout:
                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
