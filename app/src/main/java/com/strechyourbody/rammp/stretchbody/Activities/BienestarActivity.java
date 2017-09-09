package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import retrofit2.Call;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.strechyourbody.rammp.stretchbody.Entities.ProfileUser;
import com.strechyourbody.rammp.stretchbody.Entities.ResultAverage;
import com.strechyourbody.rammp.stretchbody.Entities.UserVitality;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ProfileService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;
import com.strechyourbody.rammp.stretchbody.Services.UserVitalityService;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BienestarActivity extends AppCompatActivity {

    private List<UserVitality> userVitals;
    SessionManager sessionManager;
    String userName ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienestar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(BienestarActivity.this);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(BienestarActivity.this)).build()).build();
        UserVitalityService bienestarService = retrofit.create(UserVitalityService.class);
        Call<ResultAverage> myuserVitality = bienestarService.getAverage(sessionManager.getUserDetails().getUserId().intValue());      //findAllByUserAppName(sessionManager.getUserDetails().getUsername().toString());
        sessionManager.getUserDetails().getUserId().intValue();
        ProfileService profileService = retrofit.create(ProfileService.class);
        retrofit2.Call<ProfileUser> myprofile = profileService.findProfile(sessionManager.getUserDetails().getUserId().intValue());




        myprofile.enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                // The network call was a success and we got a response
                if (response != null) {

                    userName = response.body().getName().toString();
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edit_Bienestar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editBienestar = new Intent(BienestarActivity.this,EditBienestarActivity.class);
                startActivity(editBienestar);
            }
        });

        myuserVitality.enqueue(new Callback<ResultAverage>() {
            //@Override 
            public void onResponse(Call<ResultAverage> call, Response<ResultAverage> response) {
                // The network call was a success and we got a response 
                if (response != null) {
                    buildPieChart(response.body());

                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<ResultAverage> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }

        private void buildPieChart(ResultAverage resultAverage){


            int malestar = 10;

            if(resultAverage.getAverage().equals(0)){
                Toast.makeText(BienestarActivity.this,"No hay registros",Toast.LENGTH_SHORT).show();
            }else{
                malestar = malestar - resultAverage.getAverage().intValue();
                PieChart pieChart = (PieChart) findViewById(R.id.graph);
                pieChart.getDescription().setEnabled(false);
                List<PieEntry> pieEntries = new ArrayList<>();

                pieEntries.add(new PieEntry(resultAverage.getAverage().intValue(),"Bienestar"));
                pieEntries.add(new PieEntry(malestar,"Malestar"));

                PieDataSet dataSet = new PieDataSet(pieEntries,"Bienestar de "+ userName);
                //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                //setComp1.setColors(new int[] { R.color.red1, R.color.red2, R.color.red3, R.color.red4 }, Context);

                PieData pieData = new PieData(dataSet);

                pieChart.setData(pieData);
                pieChart.invalidate();
            }

        }







    }


