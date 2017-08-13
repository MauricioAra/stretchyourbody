package com.strechyourbody.rammp.stretchbody.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.List;

/**
 * Created by mbp on 7/1/17.
 */

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    private List<Program> programs;
    private int layout;
    private OnItemClickListener itemClickListener;

    public ProgramAdapter(List<Program> programs, int layout, OnItemClickListener listener){
        this.programs = programs;
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
        holder.bind(programs.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView letter;
        public TextView initDate;
        public TextView finishDate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.text_name);
            this.letter = (ImageView) itemView.findViewById(R.id.avatar_program_letter);
            //this.initDate = (TextView) itemView.findViewById(R.id.text_init_date);
            //this.finishDate = (TextView) itemView.findViewById(R.id.text_end_date);
        }


        public void bind(final Program program,final OnItemClickListener plistener){
            ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
            int color = colorGenerator.getRandomColor();

            this.name.setText(program.getName());

            String firstLetter = String.valueOf(program.getName().charAt(0));
            TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color);
            this.letter.setImageDrawable(drawable);

            //this.initDate.setText(program.getIntDate());
            //this.finishDate.setText(program.getFinishDate());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plistener.onItemClick(program.getName(),getAdapterPosition());
                }
            });
        }
    }

    public interface  OnItemClickListener {
        void onItemClick(String name, int position);
    }


}
