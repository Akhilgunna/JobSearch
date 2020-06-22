package com.jobsearch.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.ResponseData;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.activities.InterviewScheduleActivity;
import com.jobsearch.models.ListOfAppliedJobsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfAppliedJobsAdapter extends BaseAdapter {
    List<ListOfAppliedJobsPojo> ar;
    Context cnt;
    String str_id;
    String str="http://parttimejobs.site/Jobsearch/";

    public ListOfAppliedJobsAdapter(List<ListOfAppliedJobsPojo> ar, Context cnt) {
        this.ar = ar;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return ar.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.adapter_list_of_applied_jobs, null);

        TextView tv_name = (TextView) obj2.findViewById(R.id.tv_name);
        tv_name.setText("Name:  " + ar.get(pos).getName());

        TextView tv_age = (TextView) obj2.findViewById(R.id.tv_age);
        tv_age.setText("Age:  " + ar.get(pos).getAge());

        TextView tv_exp = (TextView) obj2.findViewById(R.id.tv_exp);
        tv_exp.setText("Experience:  " + ar.get(pos).getExp());

        TextView tv_email = (TextView) obj2.findViewById(R.id.tv_email);
        tv_email.setText("Email:  "  + ar.get(pos).getEmail());

        TextView tv_Location = (TextView) obj2.findViewById(R.id.tv_Location);
        tv_Location.setText("Location:  " + ar.get(pos).getLocation());

        TextView tv_postal_code = (TextView) obj2.findViewById(R.id.tv_postal_code);
        tv_postal_code.setText("Postal Code:  " + ar.get(pos).getPostal());

        TextView tv_date = (TextView) obj2.findViewById(R.id.tv_date);
        tv_date.setText("Date:  "+ ar.get(pos).getDat());

        TextView tv_time = (TextView) obj2.findViewById(R.id.tv_time);
        tv_time.setText("Time:  "+ ar.get(pos).getTime());

        Button btn_view_resume=(Button)obj2.findViewById(R.id.btn_view_resume);
        btn_view_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("http://docs.google.com/viewer?url=" + str+ar.get(pos).getResume());
                intent.setDataAndType(uri, "text/html");
                cnt.startActivity(intent);
            }
        });

        final RadioButton radio_in_progr=(RadioButton)obj2.findViewById(R.id.radio_in_progr);
        final RadioButton radio_selected=(RadioButton)obj2.findViewById(R.id.radio_selected);
        final RadioButton radio_reje=(RadioButton)obj2.findViewById(R.id.radio_reje);


        Button btn_status=(Button)obj2.findViewById(R.id.btn_status);
        btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_in_progr.isChecked()){
                    serverData(ar.get(pos).getId(),"In Progress");
                    //Toast.makeText(cnt, "Status "+str_id, Toast.LENGTH_SHORT).show();
                }
                else if (radio_selected.isChecked()){
                    Intent intent=new Intent(cnt, InterviewScheduleActivity.class);
                    intent.putExtra("id",ar.get(pos).getId());
                    intent.putExtra("select","Selected");
                    cnt.startActivity(intent);
                    //serverData(ar.get(pos).getId(),"Selected");

                   // Toast.makeText(cnt, ""+ar.get(pos).getId(), Toast.LENGTH_SHORT).show();

                }
                else {
                    serverData(ar.get(pos).getId(),"Rejected");
                }


            }
        });
        return obj2;
    }
    ProgressDialog progressDialog;
    public void serverData(String id,String status){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.updateStatus(id,status);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(cnt,"Status updated successfully",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
