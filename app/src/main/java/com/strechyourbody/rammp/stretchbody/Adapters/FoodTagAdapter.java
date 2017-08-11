package com.strechyourbody.rammp.stretchbody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.robertlevonyan.views.chip.Chip;
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

        FoodTagAdapter.ViewHolder holder;

        if (convertView == null) {
            // Sólo si está nulo, es decir, primera vez en ser renderizado, inflamos
            // y adjuntamos las referencias del layout en una nueva instancia de nuestro
            // ViewHolder, y lo insertamos dentro del convertView, para reciclar su uso
            convertView = LayoutInflater.from(context).inflate(layout, null);
            holder = new FoodTagAdapter.ViewHolder();
            holder.tag = (Chip) convertView.findViewById(R.id.food_tag_chip);
            convertView.setTag(holder);
        } else {
            // Obtenemos la referencia que posteriormente pusimos dentro del convertView
            // Y así, reciclamos su uso sin necesidad de buscar de nuevo, referencias con FindViewById
            holder = (FoodTagAdapter.ViewHolder) convertView.getTag();
        }
        final FoodTag currentTag = getItem(position);
        holder.tag.setChipText(currentTag.getName());
        return convertView;
    }

    static class ViewHolder {
        private Chip tag;
    }
}
