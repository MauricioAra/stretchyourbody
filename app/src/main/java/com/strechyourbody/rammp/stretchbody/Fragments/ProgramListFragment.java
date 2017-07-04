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


import java.util.ArrayList;
import java.util.List;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:9000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProgramService service = retrofit.create(ProgramService.class);

       Call<List<Program>> programCall = service.listPrograms();

        programCall.enqueue(new Callback<List<Program>>() {
            @Override
            public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                List<Program> programs = response.body();
                System.out.println(programs);
            }

            @Override
            public void onFailure(Call<List<Program>> call, Throwable t) {

            }
        });


        List<String> names = new ArrayList<>();
        names.add("Rutina especial");

        mRecycler = (RecyclerView) view.findViewById(R.id.program_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter= new ProgramAdapter(names, R.layout.list_item_program, new ProgramAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
        return view;
    }




}
