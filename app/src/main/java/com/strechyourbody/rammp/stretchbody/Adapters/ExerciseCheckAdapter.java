package com.strechyourbody.rammp.stretchbody.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.strechyourbody.rammp.stretchbody.Entities.Exercise;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbp on 8/5/17.
 */

public class ExerciseCheckAdapter extends RecyclerView.Adapter<ExerciseCheckAdapter.MyViewHolder> implements Filterable {

    private List<Exercise> exercises;
    private List<Exercise> exercisesFilter;
    private List<Exercise> all;

    public ExerciseCheckAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
        this.exercisesFilter = exercises;
        this.all = exercises;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_min_item_exercise, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Exercise exercise = exercises.get(position);
        holder.name.setText(exercise.getName());
        holder.difficult.setText(exercise.getDifficulty());
        holder.repetitions.setText(exercise.getRepetition().toString());
        holder.time.setText(exercise.getTime());
        holder.calification.setText(exercise.getCalification().toString());

        if(exercise.getSelected()){
            holder.view.setBackgroundColor(Color.parseColor("#f4f4f4"));
            holder.imageView.setVisibility(View.VISIBLE);
        }else{
            holder.view.setBackgroundColor(Color.WHITE);
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        holder.view.setBackgroundColor(exercise.getSelected() ? Color.parseColor("#f4f4f4") : Color.WHITE);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise.setSelected(!exercise.getSelected());
                if(exercise.getSelected()){
                    holder.imageView.setVisibility(View.VISIBLE);
                    holder.view.setBackgroundColor(Color.parseColor("#f4f4f4"));
                }else{
                    holder.view.setBackgroundColor(Color.WHITE);
                    holder.imageView.setVisibility(View.INVISIBLE);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return exercisesFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    exercisesFilter = all;

                } else {

                    ArrayList<Exercise> filteredList = new ArrayList<>();

                    for (Exercise exercise : exercises) {

                        if (exercise.getName().toLowerCase().contains(charString) || exercise.getDifficulty().toLowerCase().contains(charString)) {

                            filteredList.add(exercise);
                        }
                    }

                    exercisesFilter = filteredList;

                }


                filterResults.values = exercisesFilter;
                filterResults.count = exercisesFilter.size();
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                exercises = (ArrayList<Exercise>) filterResults.values;
                ExerciseCheckAdapter.this.notifyDataSetChanged();
                //exercisesFilter = (ArrayList<Exercise>) filterResults.values;
                //notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        public TextView name;
        public TextView difficult;
        public TextView repetitions;
        public TextView time;
        public TextView calification;
        public ImageView imageView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            this.name = (TextView) itemView.findViewById(R.id.text_name);
            this.difficult = (TextView) itemView.findViewById(R.id.text_execise_difficulty_list);
            this.repetitions = (TextView) itemView.findViewById(R.id.text_execise_repetitions_list);
            this.time = (TextView) itemView.findViewById(R.id.text_execise_duation_list);
            this.calification = (TextView) itemView.findViewById(R.id.text_execise_calification_list);
            this.imageView = (ImageView) itemView.findViewById(R.id.img_check);
        }
    }
}
