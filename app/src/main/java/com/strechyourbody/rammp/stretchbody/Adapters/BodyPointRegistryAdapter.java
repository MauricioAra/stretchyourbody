package com.strechyourbody.rammp.stretchbody.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strechyourbody.rammp.stretchbody.Entities.BodyPoint;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPointRegistry;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.List;

/**
 * Created by dev on 8/26/17.
 */

public class BodyPointRegistryAdapter  extends RecyclerView.Adapter<BodyPointRegistryAdapter.ViewHolder>  {


    private List<BodyPointRegistry> bodyPointRegistries;
    private int layout;
    private BodyPointRegistryAdapter.OnItemClickListener itemClickListener;


    public BodyPointRegistryAdapter(List<BodyPointRegistry> bodyPointRegistries, int layout, BodyPointRegistryAdapter.OnItemClickListener itemClickListener) {
        this.bodyPointRegistries = bodyPointRegistries;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public BodyPointRegistryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        BodyPointRegistryAdapter.ViewHolder vh = new BodyPointRegistryAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(BodyPointRegistryAdapter.ViewHolder holder, int position) {
        holder.bind(bodyPointRegistries.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return bodyPointRegistries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView type;
        public TextView comment;

        public ViewHolder(View itemView) {
            super(itemView);
            this.type = (TextView) itemView.findViewById(R.id.typeLbl);
            this.comment = (TextView) itemView.findViewById(R.id.commentlbl);
        }

        public void bind(final BodyPointRegistry bodyPointRegistry, final BodyPointRegistryAdapter.OnItemClickListener plistener){
            this.type.setText(bodyPointRegistry.getType().toString());
            this.comment.setText(bodyPointRegistry.getComment().toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plistener.onItemClick();
                }
            });
        }
    }

    public interface  OnItemClickListener {
        void onItemClick();
    }



}


