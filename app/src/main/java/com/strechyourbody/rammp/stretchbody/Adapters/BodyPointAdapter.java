package com.strechyourbody.rammp.stretchbody.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPoint;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.List;

/**
 * Created by dev on 8/19/17.
 */

public class BodyPointAdapter extends RecyclerView.Adapter<BodyPointAdapter.ViewHolder> {

    private List<BodyPoint> bodyPoints;
    private int layout;
    private BodyPointAdapter.OnItemClickListener itemClickListener;

    public BodyPointAdapter(List<BodyPoint> bodyPoints, int layout, BodyPointAdapter.OnItemClickListener listener){
        this.bodyPoints = bodyPoints;
        this.layout = layout;
        this.itemClickListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        BodyPointAdapter.ViewHolder vh = new BodyPointAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(bodyPoints.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return bodyPoints.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.title_lbl);
        }

        public void bind(final BodyPoint bodyPoint, final BodyPointAdapter.OnItemClickListener plistener){
            this.name.setText(bodyPoint.getBodypart().toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plistener.onItemClick(bodyPoint.getBodypart(),getAdapterPosition());
                }
            });
        }
    }

    public interface  OnItemClickListener {
        void onItemClick(String name, int position);
    }
}
