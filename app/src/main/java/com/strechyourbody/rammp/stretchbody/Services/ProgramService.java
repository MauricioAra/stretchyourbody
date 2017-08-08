package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Program;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProgramService {

    @GET("api/app/my_programs/{id}")
    Call<List<Program>> listMyPrograms(@Path("id") int id);

    @POST("api/app/saveProgram")
    Call<Program> saveMyPrograms(@Body Program program);

    @GET("api/app/my_program/{id}")
    Call<Program> findOneProgram(@Path("id") int id);

    @DELETE("api/app/my_program/{id}")
    Call<Void> deleteProgram(@Path("id") int id);
}