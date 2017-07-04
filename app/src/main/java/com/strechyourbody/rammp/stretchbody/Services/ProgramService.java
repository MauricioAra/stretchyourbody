package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Program;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProgramService {

    @GET("programs")
    Call<List<Program>> listPrograms();
}