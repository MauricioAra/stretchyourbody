package com.strechyourbody.rammp.stretchbody.Adapters;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.strechyourbody.rammp.stretchbody.Entities.FoodTag;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulasegura on 10/8/17.
 */

public class FoodTagAdapter extends  RecyclerView.Adapter<FoodTagAdapter.MyViewHolder> {

    private List<FoodTag> tags;
    private List<FoodTag> exercisesFilter;
    private List<FoodTag> all;

    public FoodTagAdapter(List<FoodTag> tags) {
        this.tags = tags;
        this.all = tags;
    }

    @Override
    public FoodTagAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tag_food_item, parent, false);
        return new FoodTagAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodTagAdapter.MyViewHolder holder, int position) {
        final FoodTag foodTag = tags.get(position);
        holder.tag_name.setText(foodTag.getName());
        holder.view.setBackgroundColor(foodTag.getSelected() ? Color.parseColor("#a5de37") : Color.parseColor("#55dae1"));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTag.setSelected(!foodTag.getSelected());
                holder.view.setBackgroundColor(foodTag.getSelected() ? Color.parseColor("#a5de37") : Color.parseColor("#55dae1"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tag_name;
        private View view;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tag_name = (TextView) itemView.findViewById(R.id.tag_name);
        }
    }

}

