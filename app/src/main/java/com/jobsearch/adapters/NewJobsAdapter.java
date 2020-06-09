package com.jobsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jobsearch.R;
import com.jobsearch.activities.JobDetailsActivity;
import com.jobsearch.models.NewJobsPojo;

import java.util.List;

public class NewJobsAdapter extends RecyclerView.Adapter<NewJobsAdapter.MyviewHolder> {

    Context context;
    List<NewJobsPojo> a1;

    public NewJobsAdapter(Context context, List<NewJobsPojo> movieList) {
        this.context = context;
        this.a1 = movieList;
    }

    public void setMovieList(List<NewJobsPojo> a1) {
        this.a1 = a1;
        notifyDataSetChanged();
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_new_jobs, parent, false);
        return new MyviewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int pos) {
        holder.tv_compeny_name.setText("Name  :"+a1.get(pos).getCompeny_name());
        holder.tv_description.setText("Description  :"+a1.get(pos).getDescription());
        holder.tv_locoation.setText("Locoation  :"+a1.get(pos).getLocoation());
        holder.tv_salary.setText("Salary  :"+a1.get(pos).getSalary());
        holder.tv_job.setText("Job Type  :"+a1.get(pos).getJobtype());

        Glide.with(context).load(a1.get(pos).getPhoto()).into(holder.image_view);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, JobDetailsActivity.class);
                intent.putExtra("img_logo",a1.get(pos).getPhoto());
                context.startActivity(intent);
            }
        });



    }
    @Override
    public int getItemCount() {
        if (a1 != null) {
            return a1.size();
        }
        return 0;

    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tv_compeny_name, tv_description, tv_locoation,tv_salary,tv_job;
        ImageView image_view;
        CardView card_view;


        public MyviewHolder(View itemView) {
            super(itemView);
            tv_compeny_name = (TextView) itemView.findViewById(R.id.tv_compeny_name);

            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_locoation = (TextView) itemView.findViewById(R.id.tv_locoation);
            tv_salary = (TextView) itemView.findViewById(R.id.tv_salary);
            tv_job = (TextView) itemView.findViewById(R.id.tv_job);

            image_view=(ImageView)itemView.findViewById(R.id.image_view);
            card_view=(CardView)itemView.findViewById(R.id.card_view);

        }
    }
}
