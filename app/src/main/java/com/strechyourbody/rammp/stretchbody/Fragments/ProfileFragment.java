package com.strechyourbody.rammp.stretchbody.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strechyourbody.rammp.stretchbody.Entities.ProfileUser;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ProfileService;
import com.strechyourbody.rammp.stretchbody.Services.ProgramService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {
    static Context _context;

    public ProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile,container,false);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.build()).build();
        ProfileService profileService =  retrofit.create(ProfileService.class);

        Call<ProfileUser> myprofile = profileService.findProfile(1);

        myprofile.enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                // The network call was a success and we got a response
                if(response != null){
                    buildProfile(response.body());
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });


        return view;
    }

    private void buildProfile(ProfileUser profileUser){
        TextView user_name = (TextView) getActivity().findViewById(R.id.user_name);
        user_name.setText(profileUser.getName());

        TextView user_lastName = (TextView) getActivity().findViewById(R.id.user_lastName );
        user_lastName.setText(profileUser.getLastName());

        TextView user_age = (TextView) getActivity().findViewById(R.id.user_age);
        user_age.setText(profileUser.getAge());

        TextView user_gender = (TextView) getActivity().findViewById(R.id.user_gender);
        user_gender.setText(profileUser.getGender());

        TextView user_weight = (TextView) getActivity().findViewById(R.id.user_weight);
        user_weight.setText(Double.toString(profileUser.getWeight()));

        TextView user_height= (TextView) getActivity().findViewById(R.id.user_height);
        user_height.setText(Double.toString(profileUser.getHeight()));
//
//        TextView user_userId= (TextView) getActivity().findViewById(R.id.user_userId);
//        user_userId.setText(Double.toString(profileUser.getUserId()));

        TextView user_userEmail= (TextView) getActivity().findViewById(R.id.user_userEmail);
        user_userEmail.setText(profileUser.getUserEmail());

    }
}
