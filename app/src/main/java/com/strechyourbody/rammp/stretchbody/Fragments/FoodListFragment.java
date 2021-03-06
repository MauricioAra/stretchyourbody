package com.strechyourbody.rammp.stretchbody.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.strechyourbody.rammp.stretchbody.Adapters.FoodAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.Food;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.FoodService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Utils.AuthInterceptor;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodListFragment extends Fragment {

    private List<String> names;
    private RecyclerView mRecycler;
    private FoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;

    static Context _context;

    public FoodListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_food_list,container,false);
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setTitle("Hola");
        progressDialog.setMessage("Hola");
        progressDialog.show();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(this.getActivity())).build()).build();
        FoodService foodService =  retrofit.create(FoodService.class);


        Call<List<Food>> call = foodService.listFood();
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    buildList(response.body(),view);
                    progressDialog.dismiss();
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

        return view;
    }

    private void buildList(List<Food> foods,View view){
        mRecycler = (RecyclerView) view.findViewById(R.id.food_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter= new FoodAdapter(foods, R.layout.list_item_food, new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }
}
