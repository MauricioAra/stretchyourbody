package com.strechyourbody.rammp.stretchbody.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);
        idProgram = getIntent().getStringExtra("idProgram");
        repeticiones_cant = (TextView) findViewById(R.id.program_detail_repetition);
        loadProgram();
    }

    private void loadProgram(){
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
                    setToolbar(response.body());
                    setObject(response.body());
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
        program.getCantRepetition();
        repeticiones_cant.setText(program.getCantRepetition().toString());
    }


    private void setToolbar(Program program){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(program.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
