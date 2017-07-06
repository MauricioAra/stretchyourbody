package com.strechyourbody.rammp.stretchbody.Fragments;


import android.content.Context;
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
public class DashBoardFragment extends Fragment {

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

        TextView txtgloba = (TextView) view.findViewById(R.id.accion);

        txtgloba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent ira = new Intent(getActivity(), AddProgramActivity.class);
                //startActivity(ira);

                if (mOnFragmentAddProgramListener != null){
                    mOnFragmentAddProgramListener.OnRegresar();
                }
            }
        });
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
