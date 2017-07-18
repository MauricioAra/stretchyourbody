package com.strechyourbody.rammp.stretchbody.Activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.database.Cursor;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Entities.JWTToken;
import com.strechyourbody.rammp.stretchbody.Entities.UserCredentials;
import com.strechyourbody.rammp.stretchbody.Entities.UserSession;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import retrofit2.*;
import com.strechyourbody.rammp.stretchbody.Services.AuthService;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;

import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import okhttp3.OkHttpClient;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    SessionManager sessionManager;

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private Retrofit.Builder builder = RetrofitCliente.getClient("http://192.168.0.16:8080");
    private Retrofit retrofit = builder.client(httpClient.build()).build();
    private Retrofit retrofitAuth = builder.client(httpClient.addInterceptor(new AuthInterceptor(LoginActivity.this)).build()).build();
    private AuthService authService =  retrofit.create(AuthService.class);
    private AuthService authServiceWithToken =  retrofitAuth.create(AuthService.class);
    private ProgressDialog progress;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        // Set up the login form.

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.getting_started_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, BasicRegisterActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                LoginActivity.this.startActivity(i);
            }
        });
        progress = new ProgressDialog(LoginActivity.this);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

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

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            requestLogin(email, password);
        }
    }

    private boolean isEmailValid(String email) {
       // return email.contains("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x2");
        return email.contains("");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    private void handleBadCredentials() {
        View focusView;
        mEmailView.setError(getString(R.string.error_invalid_credentials));
        focusView = mEmailView;
        focusView.requestFocus();
        showProgress(false);
    }

    private void requestLogin(final String email, final String password) {
        UserCredentials userCred = new UserCredentials(email, password, true);

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
                        handleToken(token, email);
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

    private void handleToken(final JWTToken token, final String username) {

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.createSessionWithTokenOnly(token.getIdToken());

        Call<Long> call = authServiceWithToken.getUserID(username);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response != null) {
                    sessionManager.logOut();
                    Long userId = response.body();
                    UserSession session = new UserSession(username, userId, token.getIdToken());
                    createSession(session);
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                CharSequence text = getString(R.string.error_network);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                toast.show();
                showProgress(false);
            }
        });
    }
    //Creating session
    private void createSession(UserSession session) {
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.createLoginSession(session.getUserId(), session.getUsername(), session.getToken());
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}