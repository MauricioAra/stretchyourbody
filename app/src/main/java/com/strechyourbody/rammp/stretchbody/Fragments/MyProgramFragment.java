package com.strechyourbody.rammp.stretchbody.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strechyourbody.rammp.stretchbody.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProgramFragment extends Fragment {


    public MyProgramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_program,container,false);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toobar);

        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return view;
    }


}
