package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Food;
import com.strechyourbody.rammp.stretchbody.Entities.Program;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodService {
    @GET("api/app/allfood")
    Call<List<Food>> listFood();

    @GET("api/app/foodDetails/{id}")
    Call<Food> listDetaildFood(@Path("id") Integer id);
}