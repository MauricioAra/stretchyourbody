package com.strechyourbody.rammp.stretchbody.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strechyourbody.rammp.stretchbody.Entities.Food;
import com.strechyourbody.rammp.stretchbody.Entities.Recommended;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.List;

/**
 * Created by paulasegura on 15/7/17.
 */

public class FoodAdapter {
    private List<Food> recommendedsFood;
    private int layout;
    private FoodAdapter.OnItemClickListener itemClickListener;

    public FoodAdapter(List<Food> recommendedsFood, int layout, OnItemClickListener itemClickListener) {
        this.recommendedsFood = recommendedsFood;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        FoodAdapter.ViewHolder vh = new FoodAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(FoodAdapter.ViewHolder holder, int position) {
        holder.bind(recommendedsFood.get(position),itemClickListener);
    }
    @Override
    public int getItemCount() {
        return recommendedsFood.size();
    }



    public class OnItemClickListener {
        void onItemClick(String name, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.text_name);
        }


        public void bind(final Recommended recommendeds,final FoodAdapter.OnItemClickListener plistener){
            this.name.setText(recommendeds.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plistener.onItemClick(recommendeds.getName(),getAdapterPosition());
                }
            });
        }
    }
}
