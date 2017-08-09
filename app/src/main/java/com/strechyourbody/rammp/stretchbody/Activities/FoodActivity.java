package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Adapters.FoodAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.Food;
import com.strechyourbody.rammp.stretchbody.Fragments.FoodListFragment;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.FoodService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by paulasegura on 2/8/17.
 */

public class FoodActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private FoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Food> foodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_content);
        setToolbar();
        connection();
    }

    private void connection(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(FoodActivity.this)).build()).build();
        FoodService foodService =  retrofit.create(FoodService.class);


        Call<List<Food>> call = foodService.listFood();
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    buildList(response.body());
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }

    private void buildList(List<Food> foods){
        foodList = foods;
        mRecycler = (RecyclerView) findViewById(R.id.food_recycler_activity);
        mLayoutManager = new LinearLayoutManager(FoodActivity.this);
        mAdapter= new FoodAdapter(foods, R.layout.list_item_food, new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {

                Long id = foodList.get(position).getId();
                Intent intent = new Intent(FoodActivity.this, FoodDetailActivity.class);
                intent.putExtra("idFood",id.toString());
                startActivity(intent);
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }


    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comidas");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
