package com.strechyourbody.rammp.stretchbody.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.strechyourbody.rammp.stretchbody.Entities.Food;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.List;

/**
 * Created by paulasegura on 2/8/17.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private List<Food> foods;
    private int layout;
    private OnItemClickListener itemClickListener;

    public FoodAdapter(List<Food> foods, int layout, OnItemClickListener listener){
        this.foods = foods;
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
        holder.bind(foods.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView initDate;
        public TextView finishDate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.food_name_activity);
            //this.initDate = (TextView) itemView.findViewById(R.id.text_init_date);
            //this.finishDate = (TextView) itemView.findViewById(R.id.text_end_date);
        }


        public void bind(final Food food,final OnItemClickListener plistener){
            this.name.setText(food.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plistener.onItemClick(food.getName(),getAdapterPosition());
                }
            });
        }
    }

    public interface  OnItemClickListener {
        void onItemClick(String name, int position);
    }


}
