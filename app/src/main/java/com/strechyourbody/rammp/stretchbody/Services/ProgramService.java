package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Program;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProgramService {

    @GET("my_programs/{id}")
    Call<List<Program>> listMyPrograms(@Path("id") int id);

}