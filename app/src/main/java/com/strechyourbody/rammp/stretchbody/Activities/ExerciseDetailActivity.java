package com.strechyourbody.rammp.stretchbody.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.strechyourbody.rammp.stretchbody.Entities.Exercise;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ExerciseService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

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
    private ImageView favorite;
    private  List<Exercise> exerciseList;
    private int favId = -1;
    private long userId;
    private ProgressDialog progress;

    //Network
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = RetrofitCliente.getClient();
    Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(ExerciseDetailActivity.this)).build()).build();
    ExerciseService exerciseService =  retrofit.create(ExerciseService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SessionManager sessionManager = new SessionManager(ExerciseDetailActivity.this);
        userId = sessionManager.getUserDetails().getUserId();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
        imageView = (ImageView) findViewById(R.id.img_detail_exercise);
        favorite = (ImageView) findViewById(R.id.fav);
        titleView = (TextView) findViewById(R.id.textTittle);
        idExercise = getIntent().getStringExtra("idExe");
        idBody = getIntent().getStringExtra("idBody");
        counter = (TextView) findViewById(R.id.counter);
        time = (TextView) findViewById(R.id.time);

        getFavorites();

        favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(ExerciseDetailActivity.this,"Agregado a favoritos",Toast.LENGTH_LONG).show();
                toggleFavorite();
            }
        });
        progress = new ProgressDialog(ExerciseDetailActivity.this);
        progress.setTitle("Cargando...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();
        Call<Exercise> call = exerciseService.findOne(Integer.parseInt(idExercise));
        call.enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                // The network call was a success and we got a response
                if(response != null){
                    progress.hide();
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
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(this).load(exercise.getImage()).apply(options).into(imageView);
        titleView.setText(exercise.getName());
        counter.setText(exercise.getRepetition().toString()+" repeticiones");
        time.setText("Duración de 1 minuto");
    }

    private void getFavorites() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        long userId = sessionManager.getUserDetails().getUserId();

        Call<List<Exercise>> call = exerciseService.findFavorites(userId);
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                // The network call was a success and we got a response
                if(response != null) {
                    exerciseList = response.body();
                    for (Exercise ex : exerciseList) {
                        if (idExercise.equals(ex.getId().toString())) {
                            favorite.setImageResource(R.drawable.red_heart_fav);
                            favId = R.drawable.red_heart_fav;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                Toast.makeText(ExerciseDetailActivity.this,getString(R.string.error_network),Toast.LENGTH_LONG).show();
                //showProgress(false);
            }
        });
    }

    private void toggleFavorite() {
        if (favId == -1) {
            favorite.setImageResource(R.drawable.red_heart_fav);
            favId = R.drawable.red_heart_fav;
            addToFavorites();
        } else {
            favorite.setImageResource(R.drawable.red_heart);
            favId = -1;
            removeFromFavorites();
        }
    }

    private void addToFavorites() {
        Call<Void> call = exerciseService.addToFavorites(userId,Integer.parseInt(idExercise));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // The network call was a success and we got a response
                Toast.makeText(ExerciseDetailActivity.this,"Agregado a favoritos",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                CharSequence text = getString(R.string.error_network);
                Toast.makeText(ExerciseDetailActivity.this,text,Toast.LENGTH_LONG).show();
                //showProgress(false);
            }
        });
    }

    private void removeFromFavorites() {
        Call<Void> call = exerciseService.removeFromFavorites(userId,Integer.parseInt(idExercise));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(ExerciseDetailActivity.this,"Removido de favoritos",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                CharSequence text = getString(R.string.error_network);
                Toast.makeText(ExerciseDetailActivity.this,text,Toast.LENGTH_LONG).show();
                //showProgress(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (idBody.equals("fromfav")) {
                    Intent intent = new Intent(ExerciseDetailActivity.this, FavoriteExcercisesActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ExerciseDetailActivity.this,ExerciseActivity.class);
                    intent.putExtra("idBody",idBody);
                    startActivity(intent);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
