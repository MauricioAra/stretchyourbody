package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.BodyPoint;
import com.strechyourbody.rammp.stretchbody.Entities.UserVitality;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dev on 8/16/17.
 */

public interface BodyPointService {


    @GET("api/app/bodyPoints/{id}")
    Call<List<BodyPoint>> UserBodyPoints(@Path("id") Long id);


    @POST("api/app/bodyPoint")
    Call<BodyPoint> saveBodyPoint(@Body BodyPoint bodyPoint);
}
