package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.DynamicLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Adapters.BodyPointAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.ExerciseAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.ProgramAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPoint;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.BodyPointService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MalestarActivity extends AppCompatActivity {

    Toolbar toolbar;
    SessionManager sessionManager;
    private RecyclerView mRecycler;
    private BodyPointAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malestar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(MalestarActivity.this);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(MalestarActivity.this)).build()).build();
        BodyPointService bodyPointService = retrofit.create(BodyPointService.class);

        Call<List<BodyPoint>> bodyPoints = bodyPointService.UserBodyPoints(sessionManager.getUserDetails().getUserId().longValue());


        bodyPoints.enqueue(new Callback<List<BodyPoint>>() {
            @Override
            public void onResponse(Call<List<BodyPoint>> call, Response<List<BodyPoint>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    setBodyParts(response.body());
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<BodyPoint>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.go_to_edit_body_point);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(MalestarActivity.this,EditBodyPointActivity.class);
                startActivity(editProfile);
            }
        });

    }






    private void setBodyParts(final List<BodyPoint> bodyPoints){


        if(bodyPoints != null ){
            mRecycler = (RecyclerView) findViewById(R.id.bodyParts_recycler);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter= new BodyPointAdapter(bodyPoints, R.layout.list_body_points, new BodyPointAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String name, int position) {
                    Toast.makeText(MalestarActivity.this,"Alert",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MalestarActivity.this, ImprovementPointsActivity.class);
                    intent.putExtra("bodyPartName",name);
                    intent.putExtra("bodyPointID",bodyPoints.get(position).getId());
                    startActivity(intent);
                }
            });

            mRecycler.setLayoutManager(mLayoutManager);
            mRecycler.setAdapter(mAdapter);

        }else{
            Toast.makeText(MalestarActivity.this,"No hay ningun dato registrado",Toast.LENGTH_LONG).show();
        }

    }







}
