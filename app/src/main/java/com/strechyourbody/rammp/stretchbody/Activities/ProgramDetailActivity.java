package com.strechyourbody.rammp.stretchbody.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProgramDetailActivity extends AppCompatActivity {
    private String idProgram;
    private TextView repeticiones_cant;
    private TextView status_program;
    private Switch switch_status;
    private TextView no_result_text;

    private RecyclerView mRecyclerView;
    private ExerciseCheckAdapter mAdapter;
    private Program globalProgram;
    private ProgressDialog getProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);
        idProgram = getIntent().getStringExtra("idProgram");
        repeticiones_cant = (TextView) findViewById(R.id.program_detail_repetition);
        status_program = (TextView) findViewById(R.id.program_detail_status_text);
        switch_status = (Switch) findViewById(R.id.program_detail_status_switch);
        no_result_text = (TextView) findViewById(R.id.no_result_exercise_detail);
        loadProgram();
    }

    private void loadProgram(){

        getProgress = new ProgressDialog(ProgramDetailActivity.this);
        getProgress.setTitle("Cargando...");
        getProgress.setCancelable(false);
        getProgress.setIndeterminate(true);
        getProgress.show();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(ProgramDetailActivity.this)).build()).build();
        ProgramService programService =  retrofit.create(ProgramService.class);

        Call<Program> call = programService.findOneProgram(Integer.parseInt(idProgram));

        call.enqueue(new Callback<Program>() {
            @Override
            public void onResponse(Call<Program> call, Response<Program> response) {
                // The network call was a success and we got a response
                if(response != null){
                    globalProgram = response.body();
                    setToolbar(response.body());
                    setObject(response.body());
                    getProgress.hide();
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


    private void setObject(Program program){
        repeticiones_cant.setText(program.getCantRepetition().toString());
        if(program.getStatus()){
            status_program.setText("Activo");
        }else if(!program.getStatus()){
            status_program.setText("Desativado");
        }
        exercises(program.getExercises());
    }

    private void exercises(List<Exercise> exercises){
        if(exercises.size() == 0){
            no_result_text.setVisibility(View.VISIBLE);
        }else{
            for(int i = 0; i < exercises.size(); i++){
                exercises.get(i).setSelected(false);
            }
            mRecyclerView = (RecyclerView) findViewById(R.id.exercise_recycler_detail_program);
            mAdapter = new ExerciseCheckAdapter(exercises);
            LinearLayoutManager manager = new LinearLayoutManager(ProgramDetailActivity.this);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
    private void setToolbar(Program program){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(program.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_program_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.calendar_program:
                Toast.makeText(this, "Skip selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.edit_program:
                Intent edit = new Intent(ProgramDetailActivity.this,ProgramEditActivity.class);
                edit.putExtra("idProgram",idProgram);
                startActivity(edit);
                break;
            case R.id.delete_program:
                deleteAlertProgram();
                break;
            case android.R.id.home:
                Intent intent = new Intent(ProgramDetailActivity.this,ProgramActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void deleteAlertProgram(){
        AlertDialog.Builder alert = new AlertDialog.Builder(ProgramDetailActivity.this);
        alert.setTitle("Eliminar programa");
        alert.setMessage("¿Seguro que desea eliminar el programa?");
        alert.setPositiveButton("Sí", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProgram();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void deleteProgram(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(ProgramDetailActivity.this)).build()).build();
        ProgramService programService =  retrofit.create(ProgramService.class);

        Call<Void> call = programService.deleteProgram(Integer.parseInt(idProgram));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // The network call was a success and we got a response
                if(response != null){
                    loadProgram();
                    Intent programs = new Intent(ProgramDetailActivity.this,ProgramActivity.class);
                    programs.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(programs);
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }
}
