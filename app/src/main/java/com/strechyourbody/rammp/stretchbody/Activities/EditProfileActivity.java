package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;

import com.strechyourbody.rammp.stretchbody.Entities.ProfileUser;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ProfileService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    private Button button_save;
    private ProfileUser profile;
    ProfileService profileService;
    SessionManager sessionManager;
    boolean result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sessionManager = new SessionManager(EditProfileActivity.this);
        setToolbar();
        final Button button_save = (Button) findViewById(R.id.button_save);
        final Button changePassButton = (Button) findViewById(R.id.change_password);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(EditProfileActivity.this)).build()).build();
        profileService =  retrofit.create(ProfileService.class);
        Call<ProfileUser> myprofile = profileService.findProfile(sessionManager.getUserDetails().getUserId().intValue());

        myprofile.enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                // The network call was a success and we got a response
                if(response != null){
                    buildProfile(response.body());
                    profile = response.body();

                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                saveProfileInfo();
            }
        });

        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

    }


    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar perfil");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void buildProfile(ProfileUser profileUser){

        EditText user_name= (EditText) this.findViewById(R.id.User_name);
        user_name.setText(profileUser.getName());

        EditText user_lastName= (EditText) this.findViewById(R.id.user_lastName);
        user_lastName.setText(profileUser.getLastName());

        EditText user_age = (EditText) this.findViewById(R.id.user_age);
        user_age.setText(profileUser.getAge());

        if (profileUser.getGender().equals("Mujer")){
            RadioButton b = (RadioButton) findViewById(R.id.radio_femenino);
            b.setChecked(true);

        }if(profileUser.getGender().equals("Hombre") ){
            RadioButton b = (RadioButton) findViewById(R.id.radio_hombre);
            b.setChecked(true);

        }if(profileUser.getGender().equals("Otro")){
            RadioButton b = (RadioButton) findViewById(R.id.radio_otro);
            b.setChecked(true);
        }

        EditText user_weight = (EditText) this.findViewById(R.id.user_weight);
        user_weight.setText(Double.toString(profileUser.getWeight()));

        EditText user_height= (EditText) this.findViewById(R.id.user_height);
        user_height.setText(Double.toString(profileUser.getHeight()));

    }

    private void validateInfoProfile(){

        EditText ageText = (EditText) this.findViewById(R.id.user_age);
        EditText nameText = (EditText) this.findViewById(R.id.User_name);
        EditText lastNameText = (EditText) this.findViewById(R.id.user_lastName);
        EditText heigthText = (EditText) this.findViewById(R.id.user_height);
        EditText weightText = (EditText) this.findViewById(R.id.user_weight);

        //Validations
        if( nameText.getText().toString().length() == 0 ){
            nameText.setError( "Nombre es requerido!" );
            result = false;
        }
        
        if( lastNameText.getText().toString().length() == 0 ) {
            lastNameText.setError("Apellido es requerido!");
            result = false;
        }
        if( ageText.getText().toString().length() == 0 ) {
            ageText.setError("Edad es requerida!");
            result = false;
        }
        if( heigthText.getText().toString().length() == 0 ) {
            heigthText.setError("Altura es requerida!");
            result = false;
        }

        if( weightText.getText().toString().length() == 0 ) {
            weightText.setError("Peso es requerido!");
            result = false;
        }
    }


    private void saveProfileInfo(){
        result = true;
        validateInfoProfile();

        if(result == false){
            Toast.makeText(EditProfileActivity.this, "Verifique que los campos esten completos", Toast.LENGTH_SHORT).show();
        }else {
            int rgid;
            String gender;
            EditText ageText = (EditText) this.findViewById(R.id.user_age);
            EditText nameText = (EditText) this.findViewById(R.id.User_name);
            EditText lastNameText = (EditText) this.findViewById(R.id.user_lastName);
            EditText heigthText = (EditText) this.findViewById(R.id.user_height);
            EditText weightText = (EditText) this.findViewById(R.id.user_weight);
            RadioGroup genderRadio = (RadioGroup) this.findViewById(R.id.radioGender);


            rgid = genderRadio.getCheckedRadioButtonId();

            genderRadio.getCheckedRadioButtonId();


            if (rgid == R.id.radio_femenino) {
                gender = "Mujer";
            }

            if (rgid == R.id.radio_hombre) {
                gender = "Hombre";
            } else {
                gender = "Otro";
            }

            profile.setLastName(lastNameText.getText().toString());
            profile.setName(nameText.getText().toString());
            profile.setHeight(Double.parseDouble(heigthText.getText().toString()));
            profile.setWeight(Double.parseDouble(weightText.getText().toString()));
            profile.setAge(ageText.getText().toString());
            profile.setGender(gender);
            profile.setId(sessionManager.getUserDetails().getUserId());

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = RetrofitCliente.getClient();
            Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(EditProfileActivity.this)).build()).build();
            profileService = retrofit.create(ProfileService.class);
            Call<ProfileUser> save = profileService.saveUserProfile(profile);


            save.enqueue(new Callback<ProfileUser>() {

                @Override
                public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                    // The network call was a success and we got a response
                    if (response != null) {
                        Intent intent = new Intent(EditProfileActivity.this, ProfileUserActivity.class);
                        startActivity(intent);
                        Toast.makeText(EditProfileActivity.this, "Se modifico Sastifactoriamente", Toast.LENGTH_SHORT).show();
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


    }

    private void changePassword() {
        Intent i = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}

