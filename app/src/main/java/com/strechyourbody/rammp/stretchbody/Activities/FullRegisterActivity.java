package com.strechyourbody.rammp.stretchbody.Activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.strechyourbody.rammp.stretchbody.Entities.BodyPart;
import com.strechyourbody.rammp.stretchbody.R;
import android.widget.Toast;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

import com.strechyourbody.rammp.stretchbody.Services.AuthService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FullRegisterActivity extends AppCompatActivity {

    //UI References
    private EditText mAge;
    private EditText mWeight;
    private EditText mHeight;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioMale;
    private ProgressDialog progress;
    private Spinner bodyPartSpinner;
    private List<BodyPart> bodyPartList;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = RetrofitCliente.getClient();
    Retrofit retrofit = builder.client(httpClient.build()).build();
    AuthService authService =  retrofit.create(AuthService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FullRegisterActivity.this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_full_register);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGender);
        mAge = (EditText) findViewById(R.id.age);
        mWeight = (EditText) findViewById(R.id.weight);
        mHeight = (EditText) findViewById(R.id.height);
        mRadioMale = (RadioButton) findViewById(R.id.radio_hombre);
        addBodyPartsToSpinner();
        Button mContinueButton = (Button) findViewById(R.id.continue_register_button);

            mContinueButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    validateInformation();
                }

            });
        progress = new ProgressDialog(FullRegisterActivity.this);
    }

    private void addBodyPartsToSpinner() {

        Call<List<BodyPart>> call = authService.getBodyParts();
        call.enqueue(new Callback<List<BodyPart>>() {
            @Override
            public void onResponse(Call<List<BodyPart>> call, Response<List<BodyPart>> response) {
                // The network call was a success and we got a response
                if(response != null) {
                    if(response.body().size() > 0) {
                        bodyPartSpinner = (Spinner) findViewById(R.id.bodyPartSpinner);
                        List<String> list = new ArrayList<String>();
                        for (BodyPart bodyPart : response.body()) {
                            list.add(bodyPart.getName());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(FullRegisterActivity.this,
                                android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        bodyPartSpinner.setAdapter(dataAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BodyPart>> call, Throwable t) {
                CharSequence text = getString(R.string.error_network);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                toast.show();
                showProgress(false);
            }
        });
    }

    private void validateInformation() {

        String selectedBodyPart = bodyPartSpinner.getSelectedItem().toString();
        final String age = mAge.getText().toString();
        final String weight = mWeight.getText().toString();
        final String height = mHeight.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(age)) {
            mAge.setError(getString(R.string.error_field_required));
            focusView = mAge;
            cancel = true;
        }

        if(TextUtils.isEmpty(selectedBodyPart)) {
            focusView = mAge;
            cancel = true;
        }

        if (TextUtils.isEmpty(weight)) {
            mWeight.setError(getString(R.string.error_field_required));
            focusView = mWeight;
            cancel = true;
        }

        if (TextUtils.isEmpty(height)) {
            mHeight.setError(getString(R.string.error_field_required));
            focusView = mHeight;
            cancel = true;
        }

        if (mRadioGroup.getCheckedRadioButtonId() == -1) {
            mRadioMale.setError(getString(R.string.error_field_required));
            focusView = mRadioMale;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            finalRegister(age, weight, height, selectedBodyPart);
        }

    }


    private void finalRegister(String age, String weight, String height, String selectedBodyPart) {

        Integer radioGroupId = mRadioGroup.getCheckedRadioButtonId();
        String gender;
        Double parsedWeight = Double.parseDouble(weight);
        Double parsedHeight = Double.parseDouble(height);

        if (radioGroupId == R.id.radio_femenino) {
            gender = "Mujer";
        } else if (radioGroupId == R.id.radio_hombre) {
            gender = "Hombre";
        } else {
            gender = "Otro";
        }
        Bundle prevBundle = getIntent().getExtras();
        Long userId = prevBundle.getLong("userId");

        Intent i = new Intent(FullRegisterActivity.this, FinalRegisterActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putString("gender", gender);
        b.putString("age", age);
        b.putDouble("weight", parsedWeight);
        b.putDouble("height", parsedHeight);
        b.putLong("userId", userId);
        b.putString("bodyPart", selectedBodyPart);
        i.putExtras(b);
        FullRegisterActivity.this.startActivity(i);
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
