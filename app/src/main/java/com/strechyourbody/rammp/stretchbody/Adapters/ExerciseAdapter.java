package com.strechyourbody.rammp.stretchbody.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.strechyourbody.rammp.stretchbody.Activities.AddProgramActivity;
import com.strechyourbody.rammp.stretchbody.Entities.Exercise;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbp on 7/16/17.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> implements Filterable {

    private List<Exercise> exercises;
    private List<Exercise> exercisesFilter;
    private List<Exercise> all;
    private int layout;
    private ExerciseAdapter.OnItemClickListener itemClickListener;


    public ExerciseAdapter(List<Exercise> exercises, int layout, ExerciseAdapter.OnItemClickListener listener){
        this.exercises = exercises;
        this.exercisesFilter = exercises;
        all = exercises;
        this.layout = layout;
        this.itemClickListener = listener;

    }

    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ExerciseAdapter.ViewHolder vh = new ExerciseAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ExerciseAdapter.ViewHolder holder, int position) {
        holder.bind(exercises.get(position),itemClickListener);
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
                ExerciseAdapter.this.notifyDataSetChanged();
                //exercisesFilter = (ArrayList<Exercise>) filterResults.values;
                //notifyDataSetChanged();
            }
        };
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
            public TextView name;
            public TextView difficult;
            public TextView repetitions;
            public TextView time;
            public TextView calification;


            public ViewHolder(View itemView) {
                super(itemView);
                this.name = (TextView) itemView.findViewById(R.id.text_name);
                this.difficult = (TextView) itemView.findViewById(R.id.text_execise_difficulty_list);
                this.repetitions = (TextView) itemView.findViewById(R.id.text_execise_repetitions_list);
                this.time = (TextView) itemView.findViewById(R.id.text_execise_duation_list);
                this.calification = (TextView) itemView.findViewById(R.id.text_execise_calification_list);
            }


            public void bind(final Exercise exercise ,final ExerciseAdapter.OnItemClickListener plistener){
                this.name.setText(exercise.getName());
                this.difficult.setText(exercise.getDifficulty());
                this.repetitions.setText(exercise.getRepetition().toString());
                this.time.setText(exercise.getTime());
                this.calification.setText(exercise.getCalification().toString());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        plistener.onItemClick(exercise.getName(),getAdapterPosition());
                    }
                });
            }
        }

        public interface  OnItemClickListener {
            void onItemClick(String name, int position);
        }


    }


