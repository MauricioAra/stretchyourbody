package com.strechyourbody.rammp.stretchbody.Fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Adapters.DashboardAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.FoodAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.ProgramAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.Food;
import com.strechyourbody.rammp.stretchbody.Entities.Recommended;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.FoodService;
import com.strechyourbody.rammp.stretchbody.Services.RecommendedService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by paulasegura on 15/7/17.
 */

public class FoodListFragment {
    private RecyclerView mRecycler;
    private DashboardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DashBoardFragment.OnFragmentAddProgramListener mOnFragmentAddProgramListener;

    public FoodListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_food_list,container,false);
//        setHasOptionsMenu(true);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.build()).build();
        FoodService foodRecommendedService =  retrofit.create(FoodService.class);

        Call<List<Food>> call = foodRecommendedService.recommendedFood();

        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    buildList(response.body(),view);
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
    private void buildList(List<Food> recommendedsFood,View view){
        mRecycler = (RecyclerView) view.findViewById(R.id.food_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter= new FoodAdapter(recommendedsFood, R.layout.list_item_food, new DashboardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }

}
