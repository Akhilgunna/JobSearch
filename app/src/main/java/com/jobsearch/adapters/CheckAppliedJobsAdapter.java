package com.jobsearch.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.ResponseData;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.activities.EditListOfJobsActivity;
import com.jobsearch.activities.ListOfAppliedJobsActivity;
import com.jobsearch.activities.ListOfJobsActivity;
import com.jobsearch.models.CheckeAppliedJobsPojo;
import com.jobsearch.models.ListOfJobsPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckAppliedJobsAdapter extends BaseAdapter {

    List<CheckeAppliedJobsPojo> ar;
    Context cnt;
    public CheckAppliedJobsAdapter(List<CheckeAppliedJobsPojo> ar, Context cnt)
    {
        this.ar=ar;
        this.cnt=cnt;
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
    public View getView(final int pos, View view, ViewGroup viewGroup)
    {
        LayoutInflater obj1 = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2=obj1.inflate(R.layout.list_ofcheck_applied_jobs_adapter,null);



        TextView tv_jid=(TextView)obj2.findViewById(R.id.tv_jid);
        tv_jid.setText("Job Id:  "+ar.get(pos).getId());


        TextView tv_company_title=(TextView)obj2.findViewById(R.id.tv_company_title);
        tv_company_title.setText("Name:  "+ar.get(pos).getC_name());

        TextView tv_salary=(TextView)obj2.findViewById(R.id.tv_salary);
        tv_salary.setText("Salary:  "+ar.get(pos).getSalary());

        TextView tv_work_type=(TextView)obj2.findViewById(R.id.tv_work_type);
        tv_work_type.setText("Work Type:  "+ar.get(pos).getWork_type());

        TextView tv_locoation=(TextView)obj2.findViewById(R.id.tv_locoation);
        tv_locoation.setText("Location:  "+ar.get(pos).getLocation());

        TextView tv_about=(TextView)obj2.findViewById(R.id.tv_about);
        tv_about.setText("About:  "+ar.get(pos).getAbout());



        Button btn_view_applied_jobs=(Button)obj2.findViewById(R.id.btn_view_applied_jobs);

        btn_view_applied_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, ListOfAppliedJobsActivity.class);
                intent.putExtra("id",ar.get(pos).getId());
                cnt.startActivity(intent);
            }
        });
        return obj2;
    }
}