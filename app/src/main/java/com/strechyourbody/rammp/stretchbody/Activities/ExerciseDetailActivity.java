package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.strechyourbody.rammp.stretchbody.Entities.Exercise;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ExerciseService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExerciseDetailActivity extends AppCompatActivity {
    private String idExercise;
    private String idBody;
    private ImageView imageView;
    private TextView titleView;
    private TextView counter;
    private TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
        imageView = (ImageView) findViewById(R.id.img_detail_exercise);
        titleView = (TextView) findViewById(R.id.textTittle);
        idExercise = getIntent().getStringExtra("idExe");
        idBody = getIntent().getStringExtra("idBody");
        counter = (TextView) findViewById(R.id.counter);
        time = (TextView) findViewById(R.id.time);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.build()).build();
        ExerciseService exerciseService =  retrofit.create(ExerciseService.class);


        Call<Exercise> call = exerciseService.findOne(Integer.parseInt(idExercise));
        call.enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                // The network call was a success and we got a response
                if(response != null){
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle(response.body().getName());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    //globalExercises = response.body();
                    buildObject(response.body());
                    //progressDialog.dismiss();
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

    }

    private void buildObject(Exercise exercise){
        Picasso.with(this).load(exercise.getImage()).into(imageView);
        titleView.setText(exercise.getName());
        counter.setText(exercise.getRepetition().toString());
        time.setText(exercise.getTime());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ExerciseDetailActivity.this,ExerciseActivity.class);
                intent.putExtra("idBody",idBody);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
