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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Entities.BodyPart;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPoint;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPointRegistry;
import com.strechyourbody.rammp.stretchbody.Entities.ProfileUser;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.AuthService;
import com.strechyourbody.rammp.stretchbody.Services.BodyPointRegistryService;
import com.strechyourbody.rammp.stretchbody.Services.BodyPointService;
import com.strechyourbody.rammp.stretchbody.Services.ExerciseService;
import com.strechyourbody.rammp.stretchbody.Services.ProfileService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditBodyPointActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private Spinner bodyPartSpinner;
    private EditText mComment;
    private TextView title;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioBienestar;
    private BodyPointService bodyPointService;
    private BodyPointRegistryService bodyPointRegistryService;
    private BodyPoint bodyPoint = new BodyPoint();
    private BodyPointRegistry bodyPointRegistry = new BodyPointRegistry();
    private List<BodyPart> bodyParts;
    private SessionManager sessionManager;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = RetrofitCliente.getClient();
    Retrofit retrofit = builder.client(httpClient.build()).build();
    AuthService authService =  retrofit.create(AuthService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_body_point);
        sessionManager = new SessionManager(EditBodyPointActivity.this);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGender);
        Button mGuardarButton = (Button) findViewById(R.id.guardar_body_point);
        mComment = (EditText) findViewById(R.id.commentTxt);
        mRadioBienestar = (RadioButton) findViewById(R.id.radio_bienestar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addBodyPartsToSpinner();

        mGuardarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validateInformation();
            }
        });
        progress = new ProgressDialog(EditBodyPointActivity.this);
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


    private void validateInformation() {

        String selectedBodyPart = bodyPartSpinner.getSelectedItem().toString();
        final String comment = mComment.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(comment)) {
            mComment.setError(getString(R.string.error_field_required));
            focusView = mComment;
            cancel = true;
        }

        if(TextUtils.isEmpty(selectedBodyPart)) {
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
            addBodyPoint(selectedBodyPart, comment);
            //finalRegister(age, weight,height, selectedBodyPart);
        }

    }


    private void addBodyPoint(String selectedBodyPar, String comment){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(EditBodyPointActivity.this)).build()).build();
        bodyPointService =  retrofit.create(BodyPointService.class);


        Integer radioGroupId = mRadioGroup.getCheckedRadioButtonId();
        String result="";
        Long bodyID=null;
        if (radioGroupId == R.id.radio_bienestar) {
            result = "Bienestar";
        } else if (radioGroupId == R.id.radio_malestar) {
            result = "Malestar";
        }


        bodyPoint.setUserAppId(sessionManager.getUserDetails().getUserId().longValue());
      //  bodyPoint.setUserAppName(sessionManager.getUserDetails().getUsername().toString());


        bodyPointRegistry.setComment(comment);
        bodyPointRegistry.setType(result);

        for(int i=0; i<bodyParts.size();i++){

            if(bodyParts.get(i).getName() == selectedBodyPar){
                bodyID= bodyParts.get(i).getId();
            }

        }

        bodyPoint.setIdbodypart(bodyID);
        bodyPoint.setBodypart(selectedBodyPar);


        bodyPointService = retrofit.create(BodyPointService.class);
        Call<BodyPoint> save = bodyPointService.saveBodyPoint(bodyPoint);


        save.enqueue(new Callback<BodyPoint>() {

            @Override
            public void onResponse(Call<BodyPoint> call, Response<BodyPoint> response) {
                // The network call was a success and we got a response
                if (response != null) {

                    bodyPointRegistry.setBodyPointId(response.body().getId());
                    saveBodyPointRegistry();
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<BodyPoint> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

    }

    private void saveBodyPointRegistry(){
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(EditBodyPointActivity.this)).build()).build();
        bodyPointRegistryService = retrofit.create(BodyPointRegistryService.class);
        Call<BodyPointRegistry> saveRegistry = bodyPointRegistryService.saveBodyPointRegistry(bodyPointRegistry);



        saveRegistry.enqueue(new Callback<BodyPointRegistry>() {

            @Override
            public void onResponse(Call<BodyPointRegistry> call, Response<BodyPointRegistry> response) {
                // The network call was a success and we got a response
                if (response != null) {
                    Intent intent = new Intent(EditBodyPointActivity.this, MalestarActivity.class);
                    startActivity(intent);
                    Toast.makeText(EditBodyPointActivity.this, "Se agrego Satifactoriamente", Toast.LENGTH_SHORT).show();
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
                            bodyParts = response.body();
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(EditBodyPointActivity.this,
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
}
