package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.strechyourbody.rammp.stretchbody.Adapters.ExerciseAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.ExerciseCheckAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.ProgramAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.Exercise;
import com.strechyourbody.rammp.stretchbody.Entities.ProfileUser;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ExerciseService;
import com.strechyourbody.rammp.stretchbody.Services.ProfileService;
import com.strechyourbody.rammp.stretchbody.Services.ProgramService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddProgramActivity extends AppCompatActivity {
    private EditText name_text;
    private EditText cant_text;
    private Button btn_save_program;
    private SessionManager sessionManager;
    private SearchView searchView;

    private RecyclerView mRecyclerView;
    private ExerciseCheckAdapter mAdapter;
    private List<Exercise> globalExercises;

    private boolean validationForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);
        setToolbar();
        sessionManager = new SessionManager(AddProgramActivity.this);

        name_text = (EditText) findViewById(R.id.program_name_input);
        cant_text = (EditText) findViewById(R.id.program_cant_input);
        btn_save_program = (Button) findViewById(R.id.button_save_program);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setIconified(false);
        btn_save_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProgram();
            }
        });

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(AddProgramActivity.this)).build()).build();
        ExerciseService exerciseService =  retrofit.create(ExerciseService.class);

        Call<List<Exercise>> call = exerciseService.findAll();

        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    setAssFalse(response.body());
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    private void setAssFalse(List<Exercise> exercises){
        for(int i = 0; i < exercises.size(); i++){
            exercises.get(i).setSelected(false);
        }
        globalExercises = exercises;
        buildList(exercises);
    }

    private void buildList(List<Exercise> exercises){

        mRecyclerView = (RecyclerView) findViewById(R.id.exercise_recycler_add_program);
        mAdapter = new ExerciseCheckAdapter(exercises);
        LinearLayoutManager manager = new LinearLayoutManager(AddProgramActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void validateData(){
        if(name_text.getText().toString().length() == 0 ){
            name_text.setError( "Nombre es requerido!" );
            validationForm = false;
        }

        if(cant_text.getText().toString().length() == 0 ){
            cant_text.setError( "Cantidad es requerido!" );
            validationForm = false;
        }
    }

    private void saveProgram(){
        validationForm = true;
        Program program = new Program();
        List<Exercise> temporatList =  new ArrayList<>();
        validateData();

        if(!validationForm){
            Toast.makeText(AddProgramActivity.this, "Verifique que los campos esten completos", Toast.LENGTH_SHORT).show();
        }else{
            program.setName(name_text.getText().toString());
            program.setCantRepetition(Integer.parseInt(cant_text.getText().toString()));
            program.setIntDate("Init date");
            program.setFinishDate("Finish date");
            program.setRecommended(false);
            program.setInterval(0);
            program.setDairy(false);
            program.setStatus(true);
            program.setUserAppId(sessionManager.getUserDetails().getUserId());

            for (Exercise exercise : globalExercises) {
                if (exercise.getSelected()) {
                    temporatList.add(exercise);
                }
            }

            program.setExercises(temporatList);


            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = RetrofitCliente.getClient();
            Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(AddProgramActivity.this)).build()).build();
            ProgramService programService =  retrofit.create(ProgramService.class);
            Call<Program> programCall = programService.saveMyPrograms(program);

            programCall.enqueue(new Callback<Program>() {
                @Override
                public void onResponse(Call<Program> call, Response<Program> response) {
                    // The network call was a success and we got a response
                    if(response != null){
                        Intent intent = new Intent(AddProgramActivity.this,ProgramActivity.class);
                        startActivity(intent);
                    }
                    // TODO: use the repository list and display it
                }

                @Override
                public void onFailure(Call<Program> call, Throwable t) {
                    // the network call was a failure
                    // TODO: handle error
                }
            });

        }

    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agregar programa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
