package com.strechyourbody.rammp.stretchbody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPart;
import com.strechyourbody.rammp.stretchbody.Entities.SubCategory;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.List;

/**
 * Created by mbp on 7/16/17.
 */

public class BodyPartAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<BodyPart> list;

    public BodyPartAdapter(Context context, int layout, List<BodyPart> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public BodyPart getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        BodyPartAdapter.ViewHolder holder;

        if (convertView == null) {
            // Sólo si está nulo, es decir, primera vez en ser renderizado, inflamos
            // y adjuntamos las referencias del layout en una nueva instancia de nuestro
            // ViewHolder, y lo insertamos dentro del convertView, para reciclar su uso
            convertView = LayoutInflater.from(context).inflate(layout, null);
            holder = new BodyPartAdapter.ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.subcategory_id);
            holder.image = (ImageView) convertView.findViewById(R.id.subcategory_image);
            holder.name = (TextView) convertView.findViewById(R.id.subcategory_name);
            convertView.setTag(holder);
        } else {
            // Obtenemos la referencia que posteriormente pusimos dentro del convertView
            // Y así, reciclamos su uso sin necesidad de buscar de nuevo, referencias con FindViewById
            holder = (BodyPartAdapter.ViewHolder) convertView.getTag();
        }

        final BodyPart currentSubcat = getItem(position);
        Picasso.with(context).load(currentSubcat.getImage()).resize(130, 130).into(holder.image);
        holder.name.setText(currentSubcat.getName());

        return convertView;
    }

    static class ViewHolder {
        private TextView id;
        private TextView name;
        private ImageView image;
    }
}

