package com.strechyourbody.rammp.stretchbody.Services;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Path;

import com.strechyourbody.rammp.stretchbody.Entities.BodyPart;
import com.strechyourbody.rammp.stretchbody.Entities.JWTToken;
import com.strechyourbody.rammp.stretchbody.Entities.UserCredentials;
import com.strechyourbody.rammp.stretchbody.Entities.UserSingle;
import com.strechyourbody.rammp.stretchbody.Entities.UserRegister;

import java.util.List;


/**
 * Created by Mathias on 7/10/17.
 */


public interface AuthService {
    @POST("/api/authenticate")
    Call<JWTToken> authenticate(@Body UserCredentials userCred);

    @POST("api/find-user")
    Call<Long> getUserID(@Body UserSingle userSingle);

    @POST("api/register")
    Call<Long> simpleRegister(@Body UserRegister userReg);

    @POST("api/register-user-health/{userId}/{gender}/{age}/{weight}/{height}/{workHours}/{doesWorkOut}/{isSmoker}/{isHealthy}/{bodyPart}")
    Call<Void> registerUserHealth(@Path("userId") Long userId, @Path("gender") String gender, @Path("age") String age, @Path("weight") Double weight, @Path("height") Double height,
                                  @Path("workHours") Integer workHours, @Path("doesWorkOut") Boolean doesWorkout, @Path("isSmoker") Boolean isSmoker,
                                  @Path("isHealthy") Boolean eatsHealthy, @Path("bodyPart") String bodyPart);

    @GET("/api/noAuthResource/bodyParts")
    Call<List<BodyPart>> getBodyParts();

    @POST("/api/account/change_password")
    Call<Void> changePassword(@Body String newPassword);
}
