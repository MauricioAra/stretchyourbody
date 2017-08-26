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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Entities.BodyPointRegistry;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.AuthService;
import com.strechyourbody.rammp.stretchbody.Services.BodyPointRegistryService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditBodyPointRegistriesActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private EditText mComment;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioBienestar;
    private SessionManager sessionManager;
    private TextView title;
    private BodyPointRegistry bodyPointRegistry = new BodyPointRegistry();
    private BodyPointRegistryService bodyPointRegistryService;

    private String bodyName;
    private String bodyPointID;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = RetrofitCliente.getClient();
    Retrofit retrofit = builder.client(httpClient.build()).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_body_point_registries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sessionManager = new SessionManager(EditBodyPointRegistriesActivity.this);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGender);
        Button mGuardarButton = (Button) findViewById(R.id.guardar_body_point_registries);
        mComment = (EditText) findViewById(R.id.commentTxt);
        mRadioBienestar = (RadioButton) findViewById(R.id.radio_bienestar);
        bodyName = getIntent().getStringExtra("bodyPartName");
        bodyPointID = getIntent().getStringExtra("bodyPointID");
        title = (TextView) findViewById(R.id.tVMainText);
        title.setText(bodyName);

        mGuardarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validateInformation();
            }
        });
        progress = new ProgressDialog(EditBodyPointRegistriesActivity.this);
    }

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

    private void validateInformation() {

        final String comment = mComment.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(comment)) {
            mComment.setError(getString(R.string.error_field_required));
            focusView = mComment;
            cancel = true;
        }

        if (mRadioGroup.getCheckedRadioButtonId() == -1) {
            mRadioBienestar.setError(getString(R.string.error_field_required));
            focusView = mRadioBienestar;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            saveBodyPointRegistry(comment);
            //finalRegister(age, weight,height, selectedBodyPart);
        }

    }

    private void saveBodyPointRegistry(String comment){

        Long bodyID;
        Integer radioGroupId = mRadioGroup.getCheckedRadioButtonId();
        String result="";
        if (radioGroupId == R.id.radio_bienestar) {
            result = "Bienestar";
        } else if (radioGroupId == R.id.radio_malestar) {
            result = "Malestar";
        }

        bodyID = Long.parseLong(bodyPointID);
        bodyPointRegistry.setComment(comment);
        bodyPointRegistry.setType(result);
        bodyPointRegistry.setBodyPointId(bodyID);
        //bodyPointRegistry.setBodyPointBodypart(bodyName);

        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(EditBodyPointRegistriesActivity.this)).build()).build();
        bodyPointRegistryService = retrofit.create(BodyPointRegistryService.class);
        Call<BodyPointRegistry> saveRegistry = bodyPointRegistryService.saveBodyPointRegistry(bodyPointRegistry);

        saveRegistry.enqueue(new Callback<BodyPointRegistry>() {

            @Override
            public void onResponse(Call<BodyPointRegistry> call, Response<BodyPointRegistry> response) {
                // The network call was a success and we got a response
                if (response != null) {
                    Intent intent = new Intent(EditBodyPointRegistriesActivity.this, ImprovementPointsActivity.class);
                    startActivity(intent);
                    Toast.makeText(EditBodyPointRegistriesActivity.this, "Se agrego Satifactoriamente", Toast.LENGTH_SHORT).show();
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<BodyPointRegistry> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }

}
