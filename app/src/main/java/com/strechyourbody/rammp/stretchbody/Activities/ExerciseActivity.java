package com.strechyourbody.rammp.stretchbody.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import com.strechyourbody.rammp.stretchbody.Fragments.DashBoardFragment;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.BodyPartService;
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

public class ExerciseActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String idBody;
    private String idSub;
    private RecyclerView mRecycler;
    private ExerciseAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    private SessionManager session;
    private List<Exercise> globalExercises;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        setToolbar();
        session = new SessionManager(ExerciseActivity.this);

        idBody = getIntent().getStringExtra("idBody");
        idSub = getIntent().getStringExtra("idSub");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navview);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(ExerciseActivity.this)).build()).build();
        ExerciseService exerciseService =  retrofit.create(ExerciseService.class);



        Call<List<Exercise>> call = exerciseService.listExerciseByBody(Integer.parseInt(idBody));
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    globalExercises = response.body();
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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (item.getItemId()){

                    case R.id.menu_my_program:
                        Intent intent = new Intent(ExerciseActivity.this, ProgramActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_dash_board:
                        Intent intentMain = new Intent(ExerciseActivity.this, MainActivity.class);
                        startActivity(intentMain);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_my_profile:
                        Intent profile = new Intent(ExerciseActivity.this,ProfileUserActivity.class);
                        startActivity(profile);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_start_exercise:
                        Intent category = new Intent(ExerciseActivity.this,CategoryActivity.class);
                        startActivity(category);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.log_out:
                        session.logOut();
                        break;
                }


                if(fragmentTransaction) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                    item.setChecked(true);
                    getSupportActionBar().setTitle(item.getTitle());
                    drawerLayout.closeDrawers();
                }

                return true;
            }
        });

    }

    private void buildList(List<Exercise> exercises){
        mRecycler = (RecyclerView) findViewById(R.id.exercise_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter= new ExerciseAdapter(exercises, R.layout.list_item_exercise, new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Long id = globalExercises.get(position).getId();
                Intent intent = new Intent(ExerciseActivity.this, ExerciseDetailActivity.class);
                intent.putExtra("idExe",id.toString());
                intent.putExtra("idBody",idBody.toString());
                startActivity(intent);

            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

    }
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Seleccione un ejercicio");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                // abrir el menu lateral
                drawerLayout.openDrawer(GravityCompat.START);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
