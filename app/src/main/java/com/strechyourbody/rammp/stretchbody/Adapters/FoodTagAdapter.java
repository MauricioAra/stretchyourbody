package com.strechyourbody.rammp.stretchbody.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnSelectClickListener;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPart;
import com.strechyourbody.rammp.stretchbody.Entities.FoodTag;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.List;

/**
 * Created by paulasegura on 10/8/17.
 */

public class FoodTagAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<FoodTag> list;

    public FoodTagAdapter(Context context, int layout, List<FoodTag> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FoodTag getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final FoodTagAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            holder = new FoodTagAdapter.ViewHolder();
            holder.tag = (Chip) convertView.findViewById(R.id.food_tag_chip);
            convertView.setTag(holder);
        } else {
            holder = (FoodTagAdapter.ViewHolder) convertView.getTag();
        }
        final FoodTag currentTag = getItem(position);
        holder.tag.setChipText(currentTag.getName());
        holder.tag.changeSelectedBackgroundColor(currentTag.getSelected() ? Color.parseColor("#e81e63") : Color.parseColor("#ffff00") );
        holder.tag.changeSelectedBackgroundColor(currentTag.getSelected() ? Color.parseColor("#e81e63") : Color.parseColor("#ffff00"));

        holder.tag.setOnSelectClickListener(new OnSelectClickListener() {
            @Override
            public void onSelectClick(View v, boolean selected) {
                currentTag.setSelected(!currentTag.getSelected());
                holder.tag.changeSelectedBackgroundColor(currentTag.getSelected() ? Color.parseColor("#e81e63") : Color.parseColor("#ffff00"));
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private Chip tag;

    }
}
