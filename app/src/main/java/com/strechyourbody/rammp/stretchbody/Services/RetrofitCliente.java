package com.strechyourbody.rammp.stretchbody.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mbp on 7/6/17.
 */

public class RetrofitCliente {

    private static Retrofit.Builder retrofit = null;
<<<<<<< HEAD
    //http://127.0.0.1:53496
    private static String API_BASE_URL = "http://192.168.0.18:8080/";
=======


    private static String API_BASE_URL = "http://192.168.40.118:8080/";
>>>>>>> 2c789e4ec50ec30b5a47080cabb60f72171898fc

    public static Retrofit.Builder getClient(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create());
        }
        return retrofit;
    }

    public static Retrofit.Builder getClient(String url){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(
                            GsonConverterFactory.create());
        }
        return retrofit;
    }
}
