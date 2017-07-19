package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.Recommended;
import com.strechyourbody.rammp.stretchbody.Entities.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mbp on 7/15/17.
 */

public interface SubCategoryService {

    @GET("api/app/subCategoryByCategory/{id}")
    Call<List<SubCategory>> listSubcategory(@Path("id") int id);
}
