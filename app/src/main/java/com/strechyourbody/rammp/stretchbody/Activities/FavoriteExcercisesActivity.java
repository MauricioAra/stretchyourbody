package com.strechyourbody.rammp.stretchbody.Activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Adapters.ExerciseAdapter;
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

public class FavoriteExcercisesActivity extends AppCompatActivity {

    //Network
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private Retrofit.Builder builder = RetrofitCliente.getClient();
    private Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(FavoriteExcercisesActivity.this)).build()).build();
    private ExerciseService exerciseService =  retrofit.create(ExerciseService.class);
    //UI references
    private ProgressDialog progress;

    private RecyclerView mRecycler;
    private ExerciseAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private  List<Exercise> exerciseList;
    //Toolbar
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_excercises);
        requestFavorites();
        setToolbar();
        progress = new ProgressDialog(FavoriteExcercisesActivity.this);
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mis Favoritos");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
            }
        });
    }

    private void requestFavorites() {

       SessionManager sessionManager = new SessionManager(getApplicationContext());
       long userId = sessionManager.getUserDetails().getUserId();

        Call<List<Exercise>> call = exerciseService.findFavorites(userId);
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                // The network call was a success and we got a response
                if(response != null) {
                    exerciseList = response.body();
                    buildList(exerciseList);
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                CharSequence text = getString(R.string.error_network);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                toast.show();
                showProgress(false);
            }
        });
    }

    private void buildList(List<Exercise> exercises) {
        mRecycler = (RecyclerView) findViewById(R.id.exercise_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter= new ExerciseAdapter(exercises, R.layout.list_item_exercise, new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Long id = exerciseList.get(position).getId();
                Long bodyId = exerciseList.get(position).getBodyPartId();
                Intent intent = new Intent(FavoriteExcercisesActivity.this, ExerciseDetailActivity.class);
                intent.putExtra("idExe", id.toString());
                intent.putExtra("idBody", "fromfav");
                startActivity(intent);
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        progress.setTitle(getString(R.string.loading));
        progress.setMessage(getString(R.string.authenticating));
        progress.setCancelable(false);
        progress.setIndeterminate(true);

        if (show) {
            progress.show();
        } else {
            progress.dismiss();
        }
    }
}
