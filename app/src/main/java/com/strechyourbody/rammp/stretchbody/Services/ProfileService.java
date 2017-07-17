package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.ProfileUser;
import com.strechyourbody.rammp.stretchbody.Entities.Program;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dev on 7/10/17.
 */

public interface ProfileService {

    @GET("user/{id}")
    Call<ProfileUser> findProfile(@Path("id") int id);
}
