package com.evan.workoutapp.ui.exercises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.data.Exercises;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.Viewholder> {

    private Context context;
    private ArrayList<Exercises.Exercise> exerciseArrayList;

    public ExerciseAdapter(Context context, ArrayList<Exercises.Exercise> exercises) {
        this.context = context;
        this.exerciseArrayList = exercises;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout for each item in recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(com.evan.workoutapp.R.layout.exercise_card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Exercises.Exercise exercise = exerciseArrayList.get(position);
        holder.exerciseTitleTV.setText(exercise.getName());
        holder.exerciseDescriptionTV.setText(exercise.getDescription());
        holder.exerciseEquipmentTV.setText(exercise.getEquipment());
        holder.exerciseIV.setImageResource(exercise.getImageID());
    }

    @Override
    public int getItemCount() {
        // show number of cards in recyler view
        return exerciseArrayList.size();
    }

    // View holder class for initializing views (TextView & ImageView)
    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView exerciseIV;
        private TextView exerciseTitleTV, exerciseDescriptionTV, exerciseEquipmentTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            exerciseIV = itemView.findViewById(com.evan.workoutapp.R.id.exerciseCardImage);
            exerciseTitleTV = itemView.findViewById(com.evan.workoutapp.R.id.exerciseTitle);
            exerciseDescriptionTV = itemView.findViewById(com.evan.workoutapp.R.id.exerciseDescription);
            exerciseEquipmentTV = itemView.findViewById(com.evan.workoutapp.R.id.exerciseEquipment);
        }

    }

}
