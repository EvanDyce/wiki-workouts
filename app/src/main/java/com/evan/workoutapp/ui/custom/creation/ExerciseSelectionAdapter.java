package com.evan.workoutapp.ui.custom.creation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises;

import java.util.ArrayList;

public class ExerciseSelectionAdapter extends RecyclerView.Adapter<ExerciseSelectionAdapter.Viewholder> {
    private Context context;
    private ArrayList<Exercises.Exercise> exerciseArrayList;
    private WorkoutClickedListener mWorkoutClickedListener;

    public ExerciseSelectionAdapter(Context context, ArrayList<Exercises.Exercise> exercises, WorkoutClickedListener listener) {
        this.context = context;
        this.exerciseArrayList = exercises;
        this.mWorkoutClickedListener = listener;
    }

    @NonNull
    @Override
    public ExerciseSelectionAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card_layout, parent, false);
        return new Viewholder(view, mWorkoutClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseSelectionAdapter.Viewholder holder, int position) {
        Exercises.Exercise exercise = exerciseArrayList.get(position);
        holder.equipment.setText(exercise.getEquipment());
        holder.title.setText(exercise.getName());
        holder.description.setText(exercise.getDescription());
        holder.image.setImageResource(exercise.getImageID());
    }
    

    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }

    protected class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, description, equipment;
        private ImageView image;
        WorkoutClickedListener workoutClickedListener;

        public Viewholder(@NonNull View itemView, WorkoutClickedListener workoutClickedListener) {
            super(itemView);
            title = itemView.findViewById(R.id.exerciseTitle);
            description = itemView.findViewById(R.id.exerciseDescription);
            equipment = itemView.findViewById(R.id.exerciseEquipment);
            image = itemView.findViewById(R.id.exerciseCardImage);

            this.workoutClickedListener = workoutClickedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.workoutClickedListener.onWorkoutClicked(getAdapterPosition());
        }
    }

    public interface WorkoutClickedListener {
        void onWorkoutClicked(int position);
    }

}
