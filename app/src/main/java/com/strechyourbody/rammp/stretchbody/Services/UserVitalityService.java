package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.ProfileUser;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.Entities.ResultAverage;
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
    @GET("api/app/userVitality/{id}")
    Call<ResultAverage> getAverage(@Path("id") int id);

    @PUT("api/app/UserVitality")
    Call<UserVitality> saveUserVitality(@Body UserVitality userVitality);

    @POST("api/app/userVitality")
    Call<UserVitality> saveBienestar(@Body UserVitality userVitality);

}
