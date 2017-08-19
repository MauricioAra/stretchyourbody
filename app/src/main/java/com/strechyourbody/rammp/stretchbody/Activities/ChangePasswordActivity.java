package com.strechyourbody.rammp.stretchbody.Activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Entities.JWTToken;
import com.strechyourbody.rammp.stretchbody.Entities.UserCredentials;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.AuthService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePasswordActivity extends AppCompatActivity {

    //Session manager
    SessionManager sessionManager;

    //Network
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private Retrofit.Builder builder = RetrofitCliente.getClient();
    private Retrofit retrofit = builder.client(httpClient.build()).build();
    private AuthService authService =  retrofit.create(AuthService.class);

    private Retrofit retrofitWithAuth = builder.client(httpClient.addInterceptor(new AuthInterceptor(ChangePasswordActivity.this)).build()).build();
    private AuthService authServiceWithAuth =  retrofitWithAuth.create(AuthService.class);


    Button changePassButton;
    private EditText mCurrentPassword;
    private EditText mNewPassword;
    private EditText mNewPasswordVerify;
    //Spinner Loader
    private ProgressDialog progress;
    //Toolbar
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setToolbar();

        mCurrentPassword = (EditText) findViewById(R.id.current_password);
        mNewPassword = (EditText) findViewById(R.id.new_password);
        mNewPasswordVerify = (EditText) findViewById(R.id.new_password_verify);
        changePassButton = (Button) findViewById(R.id.change_password_btn);
        progress = new ProgressDialog(ChangePasswordActivity.this);

        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptChange();
            }
        });
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cambiar contraseña");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
            }
        });
    }

    private void attemptChange() {

        mCurrentPassword.setError(null);
        mNewPassword.setError(null);
        mNewPasswordVerify.setError(null);

        final String currentPassword = mCurrentPassword.getText().toString();
        final String newPassword = mNewPassword.getText().toString();
        final String newPasswordVerify = mNewPasswordVerify.getText().toString();


        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(currentPassword)) {
            mCurrentPassword.setError(getString(R.string.error_field_required));
            focusView = mCurrentPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(newPassword)) {
            mNewPassword.setError(getString(R.string.error_field_required));
            focusView = mNewPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            mNewPasswordVerify.setError(getString(R.string.error_field_required));
            focusView = mNewPasswordVerify;
            cancel = true;
        }

        if(!TextUtils.equals(newPassword, newPasswordVerify)) {
            mNewPasswordVerify.setError(getString(R.string.error_field_not_same_psw));
            focusView = mNewPasswordVerify;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            tryAuthentication(currentPassword, newPassword, newPasswordVerify);
        }
    }
    private void changePassword(final String newPassword) {

        Call<Void> call = authServiceWithAuth.changePassword(newPassword);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // The network call was a success and we got a response
                if(response != null) {
                    if(response.code() == 500) {
                        errorToast();
                    } else {
                        CharSequence text = "Se cambió la contraseña correctamente";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                        toast.show();
                        showProgress(false);
                        Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                    }
                }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorToast();
            }
        });
    }

    private void errorToast() {
        CharSequence text = getString(R.string.error_network);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.show();
        showProgress(false);
    }

    private void handleBadCredentials() {
        View focusView;
        mCurrentPassword.setError(getString(R.string.error_invalid_credentials));
        focusView = mCurrentPassword;
        focusView.requestFocus();
        showProgress(false);
    }

    private void tryAuthentication(final String currentPassword, final String newPassword, final String newPasswordVerify) {

        sessionManager = new SessionManager(getApplicationContext());
        UserCredentials userCred = new UserCredentials(sessionManager.getUserDetails().getUsername(), currentPassword, true);

        Call<JWTToken> call = authService.authenticate(userCred);
        call.enqueue(new Callback<JWTToken>() {
            @Override
            public void onResponse(Call<JWTToken> call, Response<JWTToken> response) {
                // The network call was a success and we got a response
                if(response != null) {
                    if (response.code() == 401) {
                        handleBadCredentials();
                    } else {
                        JWTToken token = response.body();
                        sessionManager.createSessionWithTokenOnly(token.getIdToken());
                        changePassword(newPassword);
                    }
                }
            }

            @Override
            public void onFailure(Call<JWTToken> call, Throwable t) {
                CharSequence text = getString(R.string.error_network);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                toast.show();
                showProgress(false);
            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        progress.setTitle(getString(R.string.loading));
        progress.setMessage("cargando");
        progress.setCancelable(false);
        progress.setIndeterminate(true);

        if (show) {
            progress.show();
        } else {
            progress.dismiss();
        }
    }

}
