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

import com.strechyourbody.rammp.stretchbody.Adapters.ProgramAdapter;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {
    private RecyclerView mRecycler;
    private ProgramAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private OnFragmentAddProgramListener mOnFragmentAddProgramListener;

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dash_board,container,false);
        setHasOptionsMenu(true);
        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.toobar);

        List<String> names = new ArrayList<>();
        names.add("Recomendaciones");

        mRecycler = (RecyclerView) view.findViewById(R.id.dash_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter= new ProgramAdapter(names, R.layout.list_item_dash, new ProgramAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

//        TextView txtgloba = (TextView) view.findViewById(R.id.accion);
//
//        txtgloba.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent ira = new Intent(getActivity(), AddProgramActivity.class);
//                //startActivity(ira);
//
//                if (mOnFragmentAddProgramListener != null){
//                    mOnFragmentAddProgramListener.OnRegresar();
//                }
//            }
//        });
        return view;
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
