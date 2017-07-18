package com.strechyourbody.rammp.stretchbody.Services;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Path;

import com.strechyourbody.rammp.stretchbody.Entities.JWTToken;
import com.strechyourbody.rammp.stretchbody.Entities.UserCredentials;
import com.strechyourbody.rammp.stretchbody.Entities.UserSingle;
import com.strechyourbody.rammp.stretchbody.Entities.UserRegister;



/**
 * Created by Mathias on 7/10/17.
 */


public interface AuthService {
    @POST("authenticate")
    Call<JWTToken> authenticate(@Body UserCredentials userCred);

    @POST("find-user")
    Call<Long> getUserID(@Body UserSingle userSingle);

    @POST("api/register")
    Call<Void> simpleRegister(@Body UserRegister userReg);

}
