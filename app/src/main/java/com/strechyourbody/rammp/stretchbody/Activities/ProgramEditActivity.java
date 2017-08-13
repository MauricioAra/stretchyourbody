package com.strechyourbody.rammp.stretchbody.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Adapters.ExerciseCheckAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.Exercise;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ExerciseService;
import com.strechyourbody.rammp.stretchbody.Services.ProgramService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProgramEditActivity extends AppCompatActivity {
    // Variables globales
    private String idProgram;
    private EditText name_text;
    private EditText cant_text;
    private RecyclerView mRecyclerView;
    private ExerciseCheckAdapter mAdapter;
    private Program globalProgram;
    private List<Exercise> globalExercises;
    private Button btn_edit;
    private ProgressDialog progress;

    // Iniciacion de retrofit
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = RetrofitCliente.getClient();
    Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(ProgramEditActivity.this)).build()).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_edit);
        idProgram = getIntent().getStringExtra("idProgram");
        name_text = (EditText) findViewById(R.id.program_name_input);
        cant_text = (EditText) findViewById(R.id.program_cant_input);
        btn_edit = (Button) findViewById(R.id.button_edit_program);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProgram();
            }
        });

        setToolbar();
        loadProgram();
    }


    // Update program
    private void updateProgram(){
        progress = new ProgressDialog(ProgramEditActivity.this);
        progress.setTitle("Modificando...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();

        globalProgram.setName(name_text.getText().toString());
        globalProgram.setCantRepetition(Integer.parseInt(cant_text.getText().toString()));
        List<Exercise> exercises = filterExercises();
        globalProgram.setExercises(exercises);

        ProgramService programService =  retrofit.create(ProgramService.class);
        Call<Program> call = programService.updateProgram(globalProgram);
        call.enqueue(new Callback<Program>() {
            @Override
            public void onResponse(Call<Program> call, Response<Program> response) {
                // The network call was a success and we got a response
                if(response != null){
                    Toast.makeText(ProgramEditActivity.this,"Se modificó el programa",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProgramEditActivity.this, ProgramActivity.class);
                    startActivity(intent);
                }
                // TODO: use the repository list and display it
            }
            @Override
            public void onFailure(Call<Program> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
                Toast.makeText(ProgramEditActivity.this,"Hubo un error a modificar al programa",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // filterExercises

    private List<Exercise> filterExercises(){
        List<Exercise> exercises = new ArrayList<>();
        for(int i = 0; i < globalExercises.size(); i++){
            if(globalExercises.get(i).getSelected()){
                exercises.add(globalExercises.get(i));
            }
        }
        return exercises;
    }

    // Load program
    private void loadProgram(){
        progress = new ProgressDialog(ProgramEditActivity.this);
        progress.setTitle("Cargando...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();

        ProgramService programService =  retrofit.create(ProgramService.class);
        Call<Program> call = programService.findOneProgram(Integer.parseInt(idProgram));
        call.enqueue(new Callback<Program>() {
            @Override
            public void onResponse(Call<Program> call, Response<Program> response) {
                // The network call was a success and we got a response
                if(response != null){
                    globalProgram = response.body();
                    setObject(response.body());
                    progress.hide();
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


    // Charge data
    private void setObject(final Program program){
        cant_text.setText(program.getCantRepetition().toString());
        name_text.setText(program.getName());

        ExerciseService exerciseService = retrofit.create(ExerciseService.class);
        Call<List<Exercise>> call = exerciseService.findAll();
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if(response != null){
                    exercises(program.getExercises(),response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {

            }
        });

    }


    // Print exercises selected
    private void exercises(List<Exercise> selected, List<Exercise> all){

        for(int i = 0; i < selected.size(); i ++){
            if(selected.get(i).getId().equals(all.get(i).getId())){
                all.get(i).setSelected(true);
            }
        }
        globalExercises = all;
        mRecyclerView = (RecyclerView) findViewById(R.id.exercise_recycler_add_program);
        mAdapter = new ExerciseCheckAdapter(all);
        LinearLayoutManager manager = new LinearLayoutManager(ProgramEditActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    // Set toolbar configuration
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar programa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
