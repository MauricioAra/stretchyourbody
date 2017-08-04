package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodService {
    @GET("allfood")
    Call<List<Food>> listFood();
}
