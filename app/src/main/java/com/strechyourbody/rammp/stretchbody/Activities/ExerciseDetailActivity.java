package com.strechyourbody.rammp.stretchbody.Activities;

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
                toggleFavorite();
            }
        });

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
                CharSequence text = getString(R.string.error_network);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                toast.show();
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
                CharSequence text = "Agregado a favoritos!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                toast.show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                CharSequence text = getString(R.string.error_network);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                toast.show();
                //showProgress(false);
            }
        });
    }

    private void removeFromFavorites() {
        Call<Void> call = exerciseService.removeFromFavorites(userId,Integer.parseInt(idExercise));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                CharSequence text = getString(R.string.error_network);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                toast.show();
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
