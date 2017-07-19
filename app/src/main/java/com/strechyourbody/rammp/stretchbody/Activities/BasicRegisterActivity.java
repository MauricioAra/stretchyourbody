package com.strechyourbody.rammp.stretchbody.Activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.strechyourbody.rammp.stretchbody.Entities.UserRegister;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.AuthService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BasicRegisterActivity extends AppCompatActivity {

    //Network references
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private Retrofit.Builder builder = RetrofitCliente.getClient();
    private Retrofit retrofit = builder.client(httpClient.build()).build();
    private AuthService authService =  retrofit.create(AuthService.class);

    //Spinner Loader
    private ProgressDialog progress;

    //UI references
    private EditText mEmailView;
    private EditText mNameView;
    private EditText mLastNameView;
    private EditText mPasswordView;
    private EditText mPasswordVerifyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmailView = (EditText) findViewById(R.id.email);
        mNameView = (EditText) findViewById(R.id.name);
        mLastNameView = (EditText) findViewById(R.id.last_name);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordVerifyView = (EditText) findViewById(R.id.password_verify);

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                atemptRegister();
            }
        });
        progress = new ProgressDialog(BasicRegisterActivity.this);
    }


    private void atemptRegister() {

        // Reset errors.
        mEmailView.setError(null);
        mNameView.setError(null);
        mLastNameView.setError(null);
        mPasswordView.setError(null);
        mPasswordVerifyView.setError(null);

        final String email = mEmailView.getText().toString();
        final String name = mNameView.getText().toString();
        final String lastName = mLastNameView.getText().toString();
        final String password = mPasswordView.getText().toString();
        final String passwordVerify = mPasswordVerifyView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if(TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        if(TextUtils.isEmpty(lastName)) {
            mLastNameView.setError(getString(R.string.error_field_required));
            focusView = mLastNameView;
            cancel = true;
        }

        if(TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if(TextUtils.isEmpty(passwordVerify)) {
            mPasswordVerifyView.setError(getString(R.string.error_field_required));
            focusView = mPasswordVerifyView;
            cancel = true;
        }

        if(!TextUtils.equals(password, passwordVerify)) {
            mPasswordVerifyView.setError(getString(R.string.error_field_not_same_psw));
            focusView = mPasswordVerifyView;
            cancel = true;
        }

        if(!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_field_short_psw));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            requestRegister(email, name, lastName, password);
        }
    }

    private boolean isEmailValid(String email) {
        // return email.contains("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x2");
        return email.contains("");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    private void requestRegister(final String email, final String name, final String lastName,
                                 final String password) {

        String login = email;
        UserRegister userReg = new UserRegister();
        userReg.setLogin(login);
        userReg.setEmail(email);
        userReg.setPassword(password);
        userReg.setFirstName(name);
        userReg.setLastName(lastName);

        Call<Void> call = authService.simpleRegister(userReg);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // The network call was a success and we got a response
                if(response != null) {
                    CharSequence text = getString(R.string.register_success);
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                    toast.show();
                    showProgress(false);
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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
