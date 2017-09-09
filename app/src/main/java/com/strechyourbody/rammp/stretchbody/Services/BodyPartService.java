package com.strechyourbody.rammp.stretchbody.Services;

import com.strechyourbody.rammp.stretchbody.Entities.BodyPart;
import com.strechyourbody.rammp.stretchbody.Entities.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mbp on 7/16/17.
 */

public interface BodyPartService {

    @GET("api/app/bodyPartBySubcategory/{id}")
    Call<List<BodyPart>> listBodyPart(@Path("id") int id);

    @GET("api/app/bodyPartByUser/{id}")
    Call<List<BodyPart>> listUserBodyPart(@Path("id") int id);
}
