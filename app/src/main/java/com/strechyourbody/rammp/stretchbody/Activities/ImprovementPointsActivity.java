package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Adapters.BodyPointAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.BodyPointRegistryAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.ExerciseAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPoint;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPointRegistry;
import com.strechyourbody.rammp.stretchbody.Entities.Exercise;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.BodyPointRegistryService;
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

public class ImprovementPointsActivity extends AppCompatActivity {

    Toolbar toolbar;
    SessionManager sessionManager;
    private RecyclerView mRecycler;
    private BodyPointRegistryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String bodyName;
    private String bodyPointID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improvement_points);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(ImprovementPointsActivity.this);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(ImprovementPointsActivity.this)).build()).build();
        bodyName = getIntent().getStringExtra("bodyPartName");
        bodyPointID = getIntent().getStringExtra("bodyPointID");
        Long bodyID;
        bodyID = Long.parseLong(bodyPointID);

        BodyPointRegistryService bodyPointRegistryService = retrofit.create(BodyPointRegistryService.class);
        Call<List<BodyPointRegistry>> bodyPointsRegistries = bodyPointRegistryService.getUserBodyPointsRegistries(bodyName);

        bodyPointsRegistries.enqueue(new Callback<List<BodyPointRegistry>>() {
            @Override
            public void onResponse(Call<List<BodyPointRegistry>> call, Response<List<BodyPointRegistry>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    setBodyPointRegistries(response.body());
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<BodyPointRegistry>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(ImprovementPointsActivity.this,EditBodyPointRegistriesActivity.class);
                startActivity(editProfile);
            }
        });
    }

    private void setBodyPointRegistries(List<BodyPointRegistry> bodyPointRegistries){

        if(bodyPointRegistries != null ){
            mRecycler = (RecyclerView) findViewById(R.id.bodyRegistries_recycler);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter= new BodyPointRegistryAdapter(bodyPointRegistries, R.layout.list_body_point_registry, new BodyPointRegistryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick() {
                    //Toast.makeText(ImprovementPointsActivity.this,"Alert",Toast.LENGTH_LONG).show();
                }
            });

            mRecycler.setLayoutManager(mLayoutManager);
            mRecycler.setAdapter(mAdapter);

        }else{
            Toast.makeText(ImprovementPointsActivity.this,"No hay ningun dato registrado",Toast.LENGTH_LONG).show();
        }

    }

}
