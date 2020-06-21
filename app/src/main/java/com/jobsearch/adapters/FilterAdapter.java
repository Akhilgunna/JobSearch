package com.jobsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jobsearch.R;
import com.jobsearch.activities.ApplyJobActivity;
import com.jobsearch.models.ListOfFiltersPojo;
import com.jobsearch.models.ListOfUserJobsPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilterAdapter extends BaseAdapter {
    List<ListOfFiltersPojo> ar9,ar;
    Context cnt;
    String str="http://parttimejobs.site/Jobsearch/";
    public FilterAdapter(List<ListOfFiltersPojo> ar1, Context cnt)
    {

        this.ar9=ar1;
        this.cnt=cnt;
        //this.ar=ar;/
        this.ar = new ArrayList<ListOfFiltersPojo>();
        this.ar.addAll(ar1);
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
        View obj2=obj1.inflate(R.layout.list_of_filter_adapter,null);

        ImageView iv_image_view=(ImageView)obj2.findViewById(R.id.iv_image_view);
        Glide.with(cnt).load(str+ar.get(pos).getImg_photo()).into(iv_image_view);

        //Toast.makeText(cnt,"test"+str+ar.get(pos).getImg_photo(),Toast.LENGTH_LONG).show();

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



        Button btn_apply=(Button)obj2.findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, ApplyJobActivity.class);
                intent.putExtra("img_logo",str+ar.get(pos).getImg_photo());
                intent.putExtra("about",ar.get(pos).getAbout());
                intent.putExtra("availability",ar.get(pos).getAvailability());
                intent.putExtra("title",ar.get(pos).getTitle());
                intent.putExtra("id",ar.get(pos).getId());
                cnt.startActivity(intent);

            }
        });
        return obj2;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ar.clear();
        if (charText.length() == 0) {
            ar.addAll(ar9);
        }
        else
        {
            for (ListOfFiltersPojo wp : ar9)
            {
                if (wp.getC_name().toLowerCase(Locale.getDefault()).contains(charText)||wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    ar.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    /*public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ar.clear();
        if (charText.length() == 0) {
            ar.addAll(ar9);
        }
        else
        {
            for (ListOfUserJobsPojo wp : ar9)
            {
                if (wp.getLocation().toLowerCase(Locale.getDefault()).contains(charText)||wp.getC_name().toLowerCase(Locale.getDefault()).contains(charText) ||wp.getSalary().toLowerCase(Locale.getDefault()).contains(charText) ||wp.getC_name().toLowerCase(Locale.getDefault()).contains(charText) ||wp.getWork_type().toLowerCase(Locale.getDefault()).contains(charText) ||wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText) ||wp.getSalary().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    ar.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }*/

}