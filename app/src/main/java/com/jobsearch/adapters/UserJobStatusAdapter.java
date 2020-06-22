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
import com.jobsearch.activities.ListOfJobsActivity;
import com.jobsearch.activities.UserJobStatusDetails;
import com.jobsearch.models.ListOfJobsPojo;
import com.jobsearch.models.UserJobStatusPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserJobStatusAdapter extends BaseAdapter {
    List<UserJobStatusPojo> ar;
    Context cnt;
    public UserJobStatusAdapter(List<UserJobStatusPojo> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.adapter_user_job_status,null);

        TextView tv_aid=(TextView)obj2.findViewById(R.id.tv_aid);
        tv_aid.setText("Application Id:  "+ar.get(pos).getId());
        TextView tv_jobid=(TextView)obj2.findViewById(R.id.tv_jobid);
        tv_jobid.setText("Job Id:  "+ar.get(pos).getJob_id());

        TextView tv_jid=(TextView)obj2.findViewById(R.id.tv_jid);
        tv_jid.setText("Job Title:  "+ar.get(pos).getJob_title());

        TextView tv_status=(TextView)obj2.findViewById(R.id.tv_status);
        tv_status.setText("Status:  "+ar.get(pos).getStatus());

        Button btn_info=(Button)obj2.findViewById(R.id.btn_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt,UserJobStatusDetails.class);
                intent.putExtra("aid",ar.get(pos).getId());
                intent.putExtra("jid",ar.get(pos).getJob_id());
                intent.putExtra("status",ar.get(pos).getStatus());
                intent.putExtra("date",ar.get(pos).getDat());
                intent.putExtra("time",ar.get(pos).getTime());
                intent.putExtra("locoation",ar.get(pos).getLocation());

                cnt.startActivity(intent);
            }
        });


        return obj2;
    }

}