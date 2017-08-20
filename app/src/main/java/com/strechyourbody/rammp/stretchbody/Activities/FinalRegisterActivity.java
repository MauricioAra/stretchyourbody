package com.strechyourbody.rammp.stretchbody.Activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.AuthService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FinalRegisterActivity extends AppCompatActivity {

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = RetrofitCliente.getClient();
    Retrofit retrofit = builder.client(httpClient.build()).build();
    AuthService authService =  retrofit.create(AuthService.class);

    private RadioGroup mRadioGroupSmokes;
    private RadioGroup mRadioGroupHealthyFood;
    private RadioGroup mRadioGroupWorkout;
    private EditText mWorkHours;
    private ProgressDialog progress;
    private RadioButton mRadioSmokes;
    private RadioButton mRadioHealtyFood;
    private RadioButton mRadioWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FinalRegisterActivity.this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_final_register);

        mRadioGroupSmokes = (RadioGroup) findViewById(R.id.radio_somkes);
        mRadioGroupHealthyFood = (RadioGroup) findViewById(R.id.radio_grouup_healthy_food);
        mRadioGroupWorkout = (RadioGroup) findViewById(R.id.radio_group_workout);
        mWorkHours = (EditText) findViewById(R.id.hours_worked);
        mRadioSmokes = (RadioButton) findViewById(R.id.does_smoke);
        mRadioHealtyFood = (RadioButton) findViewById(R.id.radio_does_eat_healthy);
        mRadioWorkout = (RadioButton) findViewById(R.id.radio_does_workout);

        Button mRegisterButton = (Button) findViewById(R.id.final_register_btn);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validateInformation();
            }

        });
        progress = new ProgressDialog(FinalRegisterActivity.this);
    }

    private void validateInformation() {

        boolean cancel = false;
        View focusView = null;

        if(mWorkHours.getText().toString() == null || mWorkHours.getText().toString().isEmpty()) {
            mWorkHours.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = mWorkHours;
        }

        if (mRadioGroupSmokes.getCheckedRadioButtonId() == -1) {
            mRadioSmokes.setError(getString(R.string.error_field_required));
            focusView = mRadioSmokes;
            cancel = true;
        }
        if (mRadioGroupHealthyFood.getCheckedRadioButtonId() == -1) {
            mRadioHealtyFood.setError(getString(R.string.error_field_required));
            focusView = mRadioHealtyFood;
            cancel = true;
        }
        if (mRadioGroupWorkout.getCheckedRadioButtonId() == -1) {
            mRadioWorkout.setError(getString(R.string.error_field_required));
            focusView = mRadioWorkout;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            attemptRegister();
        }
    }

    private void attemptRegister() {

        Integer smokesId = mRadioGroupSmokes.getCheckedRadioButtonId();
        Integer worksOutId = mRadioGroupWorkout.getCheckedRadioButtonId();
        Integer eatsHealthyId = mRadioGroupHealthyFood.getCheckedRadioButtonId();
        Boolean isSmoker;
        Boolean doesWorkout;
        Boolean eatsHealthy;
        Integer workHours = Integer.parseInt(mWorkHours.getText().toString());

        if (smokesId == R.id.does_smoke) {
            isSmoker = true;
        } else {
            isSmoker = false;
        }

        if (worksOutId == R.id.radio_does_workout) {
            doesWorkout = true;
        } else {
            doesWorkout = false;
        }

        if (eatsHealthyId == R.id.radio_does_eat_healthy) {
            eatsHealthy = true;
        } else {
            eatsHealthy = false;
        }

        Bundle b = getIntent().getExtras();
        Long userId = b.getLong("userId");
        String gender = b.getString("gender");
        String age = b.getString("age");
        Double weight = b.getDouble("weight");
        Double height = b.getDouble("height");
        String bodyPart = b.getString("bodyPart");

        Call<Void> call = authService.registerUserHealth(userId, gender, age, weight, height,
                    workHours, doesWorkout, isSmoker, eatsHealthy, bodyPart);
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
