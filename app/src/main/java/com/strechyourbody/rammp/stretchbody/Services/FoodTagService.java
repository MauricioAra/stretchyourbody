package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Food;
import com.strechyourbody.rammp.stretchbody.Entities.FoodTag;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.Entities.TagId;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by paulasegura on 12/8/17.
 */

public interface FoodTagService {
    @GET("api/app/alltags")
    Call<List<FoodTag>> listTags();

    @POST("api/app/foodtagsearch")
    Call<List<Food>> searchFood(@Body List<TagId> tagIds);
}
