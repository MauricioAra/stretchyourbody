package com.strechyourbody.rammp.stretchbody.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Adapters.BodyPartAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.ExerciseAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.ProgramAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPart;
import com.strechyourbody.rammp.stretchbody.Entities.Exercise;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.BodyPartService;
import com.strechyourbody.rammp.stretchbody.Services.ExerciseService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExerciseActivity extends AppCompatActivity {

    private String idBody;
    private String idSub;
    private RecyclerView mRecycler;
    private ExerciseAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        setToolbar();

        idBody = getIntent().getStringExtra("idBody");
        idSub = getIntent().getStringExtra("idSub");

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.build()).build();
        ExerciseService exerciseService =  retrofit.create(ExerciseService.class);



        Call<List<Exercise>> call = exerciseService.listExerciseByBody(Integer.parseInt(idBody));
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    //globalBodyParts = response.body();
                    buildList(response.body());
                    //progressDialog.dismiss();
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

    }

    private void buildList(List<Exercise> exercises){
        mRecycler = (RecyclerView) findViewById(R.id.exercise_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter= new ExerciseAdapter(exercises, R.layout.list_item_exercise, new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(ExerciseActivity.this,name,Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

    }
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Seleccione un ejercicio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ExerciseActivity.this,BodyPartActivity.class);
                intent.putExtra("id",idSub);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
