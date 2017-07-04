package com.strechyourbody.rammp.stretchbody.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.toobar);

        TextView txtgloba = (TextView) view.findViewById(R.id.accion);

        txtgloba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return view;
    }


}
