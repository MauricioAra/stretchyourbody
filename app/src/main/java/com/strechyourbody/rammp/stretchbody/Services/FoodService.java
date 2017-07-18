package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by paulasegura on 16/7/17.
 */

public class FoodService {
    @GET("recommendedFood")
    Call<List<Food>> recommendedFood();
}

