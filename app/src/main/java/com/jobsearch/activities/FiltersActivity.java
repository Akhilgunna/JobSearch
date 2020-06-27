package com.jobsearch.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jobsearch.EndPointUrl;
import com.jobsearch.R;
import com.jobsearch.RetrofitInstance;
import com.jobsearch.adapters.FilterAdapter;
import com.jobsearch.adapters.SearchJobsAdapter;
import com.jobsearch.models.ListOfFiltersPojo;
import com.jobsearch.models.ListOfUserJobsPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiltersActivity extends AppCompatActivity {
    EditText search, et_location;
    Spinner spin_job_type, spin_sal;
    Button btn_submit;
    ListView list_view;
    ProgressDialog progressDialog;
    List<ListOfFiltersPojo> a1;
    FilterAdapter filterAdapter;
    SeekBar simpleSeekBar;
    int progressChangedValue = 0;
    TextView tv_salary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        getSupportActionBar().setTitle("Search Filters");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        search = (EditText) findViewById(R.id.search);
        et_location = (EditText) findViewById(R.id.et_location);
        spin_job_type = (Spinner) findViewById(R.id.spin_job_type);
        //spin_sal = (Spinner) findViewById(R.id.spin_sal);

        simpleSeekBar=(SeekBar)findViewById(R.id.simpleSeekBar);
        tv_salary=(TextView)findViewById(R.id.tv_salary);


        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(FiltersActivity.this, "Seek bar progress is :" + progressChangedValue,Toast.LENGTH_SHORT).show();
                tv_salary.setText("Salary is:  "+progressChangedValue+"$");

            }
        });

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiltersActivity.this, SearchJobsActivity.class);
                intent.putExtra("search", search.getText().toString());
                intent.putExtra("location", et_location.getText().toString());
                intent.putExtra("job_type", spin_job_type.getSelectedItem().toString());
                //intent.putExtra("salary", spin_sal.getSelectedItem().toString());
                intent.putExtra("salary",progressChangedValue);
                startActivity(intent);
            }
        });
        list_view=(ListView)findViewById(R.id.list_view);

        a1= new ArrayList<>();
        serverData();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                filterAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

        /*a1= new ArrayList<>();
        serverData();
*/
    }

    public void serverData(){
        progressDialog = new ProgressDialog(FiltersActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<ListOfFiltersPojo>> call = service.searchfilter();
        call.enqueue(new Callback<List<ListOfFiltersPojo>>() {
            @Override
            public void onResponse(Call<List<ListOfFiltersPojo>> call, Response<List<ListOfFiltersPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(FiltersActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    a1 = response.body();
                    filterAdapter=new FilterAdapter(a1,FiltersActivity.this);
                    list_view.setAdapter(filterAdapter);
                   // list_view.setAdapter(new FilterAdapter(a1, FiltersActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<ListOfFiltersPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FiltersActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
