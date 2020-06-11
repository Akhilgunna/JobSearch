package com.jobsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jobsearch.R;
import com.jobsearch.models.ListOfAppliedJobsPojo;

import java.util.List;

public class ListOfAppliedJobsAdapter extends BaseAdapter {
    List<ListOfAppliedJobsPojo> ar;
    Context cnt;
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
        tv_name.setText("Name  :" + ar.get(pos).getName());

        TextView tv_age = (TextView) obj2.findViewById(R.id.tv_age);
        tv_age.setText("Age  :" + ar.get(pos).getAge());

        TextView tv_exp = (TextView) obj2.findViewById(R.id.tv_exp);
        tv_exp.setText("Experience  :" + ar.get(pos).getExp());

        TextView tv_email = (TextView) obj2.findViewById(R.id.tv_email);
        tv_email.setText("Email  :" + ar.get(pos).getEmail());

        TextView tv_Location = (TextView) obj2.findViewById(R.id.tv_Location);
        tv_Location.setText("Location  :" + ar.get(pos).getLocation());

        TextView tv_postal_code = (TextView) obj2.findViewById(R.id.tv_postal_code);
        tv_postal_code.setText("Postal Code  :" + ar.get(pos).getPostal());

        TextView tv_date = (TextView) obj2.findViewById(R.id.tv_date);
        tv_date.setText("Date  :" + ar.get(pos).getDat());

        TextView tv_time = (TextView) obj2.findViewById(R.id.tv_time);
        tv_time.setText("Time  :" + ar.get(pos).getTime());

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




        return obj2;
    }
}
