package com.strechyourbody.rammp.stretchbody.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.strechyourbody.rammp.stretchbody.R;

import java.util.List;

/**
 * Created by mbp on 7/1/17.
 */

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    private List<String> names;
    private int layout;
    private OnItemClickListener itemClickListener;

    public ProgramAdapter(List<String> names, int layout, OnItemClickListener listener){
        this.names = names;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(names.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.text_name);
        }


        public void bind(final String pname,final OnItemClickListener plistener){
            this.name.setText(pname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plistener.onItemClick(pname,getAdapterPosition());
                }
            });
        }
    }

    public interface  OnItemClickListener {
        void onItemClick(String name, int position);
    }


}
