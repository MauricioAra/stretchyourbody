package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Exercise;
import com.strechyourbody.rammp.stretchbody.Entities.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mbp on 7/16/17.
 */

public interface ExerciseService {
    @GET("api/app/exercise_by_body/{id}")
    Call<List<Exercise>> listExerciseByBody(@Path("id") int id);

    @GET("api/app/exercise_by_id/{id}")
    Call<Exercise> findOne(@Path("id") int id);

    @GET("api/app/all_exercise")
    Call<List<Exercise>> findAll();

    @GET("api/user-apps/favorite-exercises/{id}")
    Call<List<Exercise>> findFavorites(@Path("id") long id);
}
