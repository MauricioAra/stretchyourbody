package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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

public class ProfileUserActivity extends AppCompatActivity {
    Toolbar toolbar;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        sessionManager = new SessionManager(ProfileUserActivity.this);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(ProfileUserActivity.this)).build()).build();
        ProfileService profileService =  retrofit.create(ProfileService.class);
        Call<ProfileUser> myprofile = profileService.findProfile(sessionManager.getUserDetails().getUserId().intValue());

        myprofile.enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                // The network call was a success and we got a response
                if(response != null){
                    buildProfile(response.body());
                    toolbar = (Toolbar) findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    toolbar.setTitle(response.body().getName() +" "+ response.body().getLastName());
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_go_edit_profile);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(ProfileUserActivity.this,EditProfileActivity.class);
                startActivity(editProfile);
            }
        });
    }

    private void buildProfile(ProfileUser profileUser){

        TextView user_age = (TextView) this.findViewById(R.id.user_age);
        user_age.setText(profileUser.getAge());

        TextView user_gender = (TextView) this.findViewById(R.id.user_gender);
        user_gender.setText(profileUser.getGender());

        TextView user_weight = (TextView) this.findViewById(R.id.user_weight);
        user_weight.setText(Double.toString(profileUser.getWeight()));

        TextView user_height= (TextView) this.findViewById(R.id.user_height);
        user_height.setText(Double.toString(profileUser.getHeight()));

        TextView user_userEmail= (TextView) this.findViewById(R.id.user_userEmail);
        user_userEmail.setText(profileUser.getUserEmail());

    }
}
