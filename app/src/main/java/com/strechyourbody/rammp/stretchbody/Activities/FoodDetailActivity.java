package com.strechyourbody.rammp.stretchbody.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.strechyourbody.rammp.stretchbody.Entities.Food;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.FoodService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FoodDetailActivity extends AppCompatActivity {
    private String idFood;
    private ImageView imageFood;
    private TextView titleView;
    private TextView descriptionView;
    private ProgressDialog progress;

    private Food foodGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        idFood = getIntent().getStringExtra("idFood");
        imageFood = (ImageView) findViewById(R.id.food_img);
        titleView = (TextView) findViewById(R.id.food_name);
        descriptionView = (TextView) findViewById(R.id.food_description);

        progress = new ProgressDialog(FoodDetailActivity.this);
        progress.setTitle("Cargando...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();



        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(FoodDetailActivity.this)).build()).build();
        FoodService foodService =  retrofit.create(FoodService.class);

        Call<Food> call = foodService.listDetaildFood(Integer.parseInt(idFood));

            call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                // The network call was a success and we got a response
                if(response != null){
                    progress.hide();
                    foodGlobal = response.body();
                    buildObject(response.body());
                    setToolbar(response.body().getName());

                }
                // TODO: use the repository list and display it
            }

            private void buildObject(Food food){
                Picasso.with(FoodDetailActivity.this).load(food.getImage()).into(imageFood);
                titleView.setText(food.getName());
                descriptionView.setText(food.getDescription());
            }


                @Override
            public void onFailure(Call<Food> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

    }

    private void setToolbar(String name){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
