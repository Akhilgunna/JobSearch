package com.jobsearch;




import com.jobsearch.models.EditProfilePojo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface EndPointUrl {


    @GET("Jobsearch/user_registration.php")
    Call<ResponseData> user_registration(
            @Query("fname") String fname,
            @Query("phone") String phone,
            @Query("emailid") String emailid,
            @Query("lname") String lname,
            @Query("pwd") String pwd,
            @Query("utype") String utype

    );

    @GET("Jobsearch/login.php")
    Call<ResponseData> user_login(
            @Query("emailid") String emailid,
            @Query("pwd") String pwd
    );


    @GET("Jobsearch/my_profile.php")
    Call<List<EditProfilePojo>> getMyProfile
            (
                    @Query("email") String email
            );


    @GET("Jobsearch/update_profile.php")
    Call<ResponseData> update_user_profile(

            @Query("fname") String fname,
            @Query("lname") String lname,
            @Query("phone") String phone,
            @Query("email") String email,
            @Query("pwd") String pwd
    );




}