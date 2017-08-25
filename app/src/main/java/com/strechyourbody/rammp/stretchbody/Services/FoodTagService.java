package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.FoodTag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by paulasegura on 12/8/17.
 */

public interface FoodTagService {
    @GET("api/app/allTags")
    Call<List<FoodTag>> listTags();
}
