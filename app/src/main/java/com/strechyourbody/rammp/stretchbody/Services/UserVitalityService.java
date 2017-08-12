package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.ProfileUser;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.Entities.UserVitality;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by dev on 7/30/17.
 */

public interface UserVitalityService {
    @GET("api/app/userVitality/{userVitality}")
    Call<List<UserVitality>> findAllByUserAppName(@Path("userVitality") String username);

    @PUT("api/app/UserVitality")
    Call<UserVitality> saveUserVitality(@Body UserVitality userVitality);

    @GET("api/app/userVitality/{id}")
    Call<UserVitality> findVitality(@Path("id") int id);

    @GET("api/app/userVitalities")
    Call<List<UserVitality>> findAll();

    @POST("api/app/userVitality")
    Call<UserVitality> saveBienestar(@Body UserVitality userVitality);


}
