package com.jobsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jobsearch.R;
import com.jobsearch.activities.UserJobStatusDetails;
import com.jobsearch.models.UserJobStatusPojo;
import com.jobsearch.models.UserJobstatusDetailsPojo;

import java.util.List;

public class UserJobStatusDetailsAdapter extends BaseAdapter {
    List<UserJobstatusDetailsPojo> ar;
    Context cnt;
    String str="http://parttimejobs.site/Jobsearch/";
    public UserJobStatusDetailsAdapter(List<UserJobstatusDetailsPojo> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.list_job_status_details_adapter,null);


        ImageView iv_image_view=(ImageView)obj2.findViewById(R.id.iv_image_view);
        Glide.with(cnt).load(str+ar.get(pos).getImg_photo()).into(iv_image_view);

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

        return obj2;
    }

}