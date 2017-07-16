package com.strechyourbody.rammp.stretchbody.Services;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Path;

import com.strechyourbody.rammp.stretchbody.Entities.JWTToken;
import com.strechyourbody.rammp.stretchbody.Entities.UserCredentials;


/**
 * Created by Mathias on 7/10/17.
 */


public interface AuthService {
    @POST("/api/authenticate")
    Call<JWTToken> authenticate(@Body UserCredentials userCred);

    @GET("api/find-user/{username}")
    Call<Long> getUserID(@Path("username") String username);
}
