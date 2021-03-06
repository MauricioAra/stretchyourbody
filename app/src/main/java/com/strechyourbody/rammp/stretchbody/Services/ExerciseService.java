package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("api/user-apps/favorite-exercises/{userId}")
    Call<List<Exercise>> findFavorites(@Path("userId") long id);

    @POST("api/users/add-to-favorites/{userId}/{exerciseId}")
    Call<Void> addToFavorites(@Path("userId") long id, @Path("exerciseId") long exerciseId);

    @POST("api/users/remove-from-favorites/{userId}/{exerciseId}")
    Call<Void> removeFromFavorites(@Path("userId") long id, @Path("exerciseId") long exerciseId);

    @POST("api/users/rate-exercise/{exerciseId}/{rating}")
    Call<Void> rateExercise(@Path("exerciseId") long id, @Path("rating") float rating);
}
