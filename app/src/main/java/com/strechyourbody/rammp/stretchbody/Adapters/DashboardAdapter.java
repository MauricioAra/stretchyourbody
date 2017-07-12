package com.strechyourbody.rammp.stretchbody.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strechyourbody.rammp.stretchbody.Entities.Recommended;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.List;

/**
 * Created by paulasegura on 4/7/17.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<Recommended> recommendeds;
    private int layout;
    private DashboardAdapter.OnItemClickListener itemClickListener;

    public DashboardAdapter(List<Recommended> recommendeds, int layout, DashboardAdapter.OnItemClickListener listener){
        this.recommendeds = recommendeds;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        DashboardAdapter.ViewHolder vh = new DashboardAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(DashboardAdapter.ViewHolder holder, int position) {
        holder.bind(recommendeds.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return recommendeds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.text_name);
        }


        public void bind(final Recommended recommendeds,final DashboardAdapter.OnItemClickListener plistener){
            this.name.setText(recommendeds.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plistener.onItemClick(recommendeds.getName(),getAdapterPosition());
                }
            });
        }
    }

    public interface  OnItemClickListener {
        void onItemClick(String name, int position);
    }
}
