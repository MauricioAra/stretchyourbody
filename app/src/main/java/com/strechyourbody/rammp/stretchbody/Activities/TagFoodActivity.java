package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.strechyourbody.rammp.stretchbody.Adapters.ExerciseAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.FoodTagAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPart;
import com.strechyourbody.rammp.stretchbody.Entities.FoodTag;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.FoodTagService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.R.attr.data;

public class TagFoodActivity extends AppCompatActivity {
    private GridView gridViewTagFood;
    private FoodTagAdapter foodTagAdapter;
    private List<FoodTag> foodTagList;
    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_food);
        setToolbar();


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(TagFoodActivity.this)).build()).build();
        FoodTagService foodTagServiceService =  retrofit.create(FoodTagService.class);

        Call<List<FoodTag>> call = foodTagServiceService.listTags();
        call.enqueue(new Callback<List<FoodTag>>() {

            @Override
            public void onResponse(Call<List<FoodTag>> call, Response<List<FoodTag>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    buildGrid(response.body());
                    foodTagList = response.body();
                    //progressDialog.dismiss();
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<FoodTag>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }


    private void buildGrid(List<FoodTag> ptags){
        for(int i = 0; i < ptags.size();i++){
            ptags.get(i).setSelected(false);
        }
        mRecycler = (RecyclerView) findViewById(R.id.grid_view_tag);
        int numberOfColumns = 3;
        mRecycler.setLayoutManager(new GridLayoutManager(TagFoodActivity.this, numberOfColumns));
        foodTagAdapter= new FoodTagAdapter(ptags);
        mRecycler.setAdapter(foodTagAdapter);
    }
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Selecciona los tags");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
