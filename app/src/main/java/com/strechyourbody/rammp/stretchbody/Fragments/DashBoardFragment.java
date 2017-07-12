package com.strechyourbody.rammp.stretchbody.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Adapters.DashboardAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.ProgramAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.Entities.Recommended;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ProgramService;
import com.strechyourbody.rammp.stretchbody.Services.RecommendedService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {
    private RecyclerView mRecycler;
    private DashboardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private OnFragmentAddProgramListener mOnFragmentAddProgramListener;

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_dash_board,container,false);
        setHasOptionsMenu(true);

        String API_BASE_URL = "http://192.168.40.154:8080/api/app/";
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient(API_BASE_URL);
        Retrofit retrofit = builder.client(httpClient.build()).build();
        RecommendedService recommendedService =  retrofit.create(RecommendedService.class);

        Call<List<Recommended>> call = recommendedService.listRecommended();

        call.enqueue(new Callback<List<Recommended>>() {
            @Override
            public void onResponse(Call<List<Recommended>> call, Response<List<Recommended>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    buildList(response.body(),view);
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<Recommended>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });


        return view;
    }

    private void buildList(List<Recommended> recommendeds,View view){
        mRecycler = (RecyclerView) view.findViewById(R.id.dash_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter= new DashboardAdapter(recommendeds, R.layout.list_item_dash, new DashboardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentAddProgramListener){
            mOnFragmentAddProgramListener = (OnFragmentAddProgramListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentAddProgramListener {
        void OnRegresar();
    }


}
