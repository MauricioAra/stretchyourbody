package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.BodyPointRegistry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dev on 8/20/17.
 */

public interface BodyPointRegistryService {

    @POST("api/app/bodyPointRegistry")
    Call<BodyPointRegistry> saveBodyPointRegistry(@Body BodyPointRegistry bodyPointRegistry);

    @GET("api/app/bodyPointRegistry/{id}")
    Call<List<BodyPointRegistry>> getUserBodyPointRegistry(@Path("id") Long id);

    @GET("api/app/BodyPointsRegistries/{id}")
    Call<List<BodyPointRegistry>> getUserBodyPointsRegistries(@Path("id") String id);




}