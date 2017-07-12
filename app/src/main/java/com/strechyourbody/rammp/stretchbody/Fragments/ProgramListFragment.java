package com.strechyourbody.rammp.stretchbody.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.strechyourbody.rammp.stretchbody.Adapters.ProgramAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ProgramService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;


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
public class ProgramListFragment extends Fragment {

    private List<String> names;
    private RecyclerView mRecycler;
    private ProgramAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    static Context _context;

    public ProgramListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_program_list,container,false);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.build()).build();
        ProgramService programService =  retrofit.create(ProgramService.class);


        Call<List<Program>> call = programService.listMyPrograms(1);

        call.enqueue(new Callback<List<Program>>() {
            @Override
            public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    buildList(response.body(),view);
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<Program>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

        return view;
    }

    private void buildList(List<Program> programs,View view){
        mRecycler = (RecyclerView) view.findViewById(R.id.program_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter= new ProgramAdapter(programs, R.layout.list_item_program, new ProgramAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }
}
