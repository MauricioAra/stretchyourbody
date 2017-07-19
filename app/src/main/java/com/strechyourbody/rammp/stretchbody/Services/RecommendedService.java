package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.Entities.Recommended;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by paulasegura on 11/7/17.
 */

public interface RecommendedService {
    @GET("api/app/programsRecommended")
    Call<List<Recommended>> listRecommended();
}
