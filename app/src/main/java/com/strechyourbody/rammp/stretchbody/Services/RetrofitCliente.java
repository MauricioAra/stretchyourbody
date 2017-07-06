package com.strechyourbody.rammp.stretchbody.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mbp on 7/6/17.
 */

public class RetrofitCliente {

    private static Retrofit.Builder retrofit = null;

    public static Retrofit.Builder getClient(String baseUrl){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(
                            GsonConverterFactory.create());
        }
        return retrofit;
    }
}
