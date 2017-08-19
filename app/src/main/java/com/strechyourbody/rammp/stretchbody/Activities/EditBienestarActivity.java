package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Entities.ProfileUser;
import com.strechyourbody.rammp.stretchbody.Entities.UserVitality;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ProfileService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;
import com.strechyourbody.rammp.stretchbody.Services.UserVitalityService;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditBienestarActivity extends AppCompatActivity {
    SeekBar seekBarRange;
    SessionManager sessionManager;
    String userName ="Rodny";
    private UserVitality userVitality = new UserVitality();
    boolean result=true;
    UserVitalityService bienestarService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bienestar);
        setToolbar();
        sessionManager = new SessionManager(EditBienestarActivity.this);
        final Button button_save = (Button) findViewById(R.id.button_save);
        seekBarRange=(SeekBar)findViewById(R.id.seekBar);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(EditBienestarActivity.this)).build()).build();

        bienestarService = retrofit.create(UserVitalityService.class);
        ProfileService profileService = retrofit.create(ProfileService.class);
        Call<ProfileUser> myprofile = profileService.findProfile(sessionManager.getUserDetails().getUserId().intValue());


        seekBarRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }


            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

                Toast.makeText(EditBienestarActivity.this, "Bienestar en:"+ seekBar.getProgress(), Toast.LENGTH_SHORT).show();

            }
        });


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                saveBienestar();

            }
        });

        myprofile.enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                // The network call was a success and we got a response
                if (response != null) {

                    userName = response.body().getName();
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }


    private void validateInfoBienestar(){
        EditText commentTxt = (EditText) this.findViewById(R.id.commentTxt);

        //Validations
        if( commentTxt.getText().toString().length() == 0 ){
            commentTxt.setError( "Comentario es requerido!" );
            result = false;
        }
    }

    private void saveBienestar(){
        result = true;
        validateInfoBienestar();

        if(result){
            EditText commentTxt = (EditText) this.findViewById(R.id.commentTxt);

            userVitality.setUserAppID(sessionManager.getUserDetails().getUserId().longValue());
            userVitality.setComment(commentTxt.getText().toString());
            userVitality.setRange(seekBarRange.getProgress());


            Call<UserVitality> save = bienestarService.saveBienestar(userVitality);

            save.enqueue(new Callback<UserVitality>() {

                @Override
                public void onResponse(Call<UserVitality> call, Response<UserVitality> response) {
                    // The network call was a success and we got a response
                    if (response != null) {
                        Intent intent = new Intent(EditBienestarActivity.this, BienestarActivity.class);
                        startActivity(intent);
                        Toast.makeText(EditBienestarActivity.this, "Se modifico Sastifactoriamente", Toast.LENGTH_SHORT).show();
                    }
                    // TODO: use the repository list and display it
                }

                @Override
                public void onFailure(Call<UserVitality> call, Throwable t) {
                    // the network call was a failure
                    // TODO: handle error
                }
            });

        }else{
            Toast.makeText(EditBienestarActivity.this, "Verifique que los campos esten completos", Toast.LENGTH_SHORT).show();
        }

    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agregar estado de bienestar");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
