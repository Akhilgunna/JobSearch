package com.jobsearch;




import com.jobsearch.models.EditMyJobsPojo;
import com.jobsearch.models.EditProfilePojo;
import com.jobsearch.models.ListOfJobsPojo;
import com.jobsearch.models.MyProfilePOJO;
import com.jobsearch.models.UploadObject;

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


//    @GET("Jobsearch/user_registration.php")
//    Call<ResponseData> ur(
//            @Query("fname") String fname,
//            @Query("phone") String phone,
//            @Query("emailid") String emailid,
//            @Query("lname") String lname,
//            @Query("pwd") String pwd,
//            @Query("gender") String gender,
//            @Query("utype") String utype
//
//    );

    @Multipart
    @POST("Jobsearch/user_registration.php")
    Call<ResponseData> user_registration(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );
    @Multipart
    @POST("Jobsearch/post_job.php")
    Call<ResponseData> post_job(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );




    @GET("Jobsearch/changepassword.php")
    Call<ResponseData> change_password(
            @Query("emailid") String emailid,
            @Query("pwd") String pwd
    );

    @GET("Jobsearch/login.php")
    Call<ResponseData> user_login(
            @Query("emailid") String emailid,
            @Query("pwd") String pwd,
            @Query("utype") String utype
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

    @GET("Jobsearch/forgotPassword.php")
    Call<ResponseData> forgotPassword
            (

                    @Query("emailid") String emailid
            );


    @Multipart
    @POST("Jobsearch/test.php?")
    Call<ResponseData> test(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );

    @GET("Jobsearch/getMyPostJobs.php")
    Call<List<ListOfJobsPojo>> listofJobs(
            @Query("email") String email
    );
    @GET("Jobsearch/get_job.php")
    Call<List<EditMyJobsPojo>> EditMyJob
            (
                    @Query("email") String email
            );

    @GET("Jobsearch/update_job.php")
    Call<ResponseData> update_job(

            @Query("title") String title,
            @Query("c_name") String c_name,
            @Query("location") String location,
            @Query("qualification") String qualification,
            @Query("about") String about,
            @Query("salary") String salary
    );


}