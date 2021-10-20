package com.evan.workoutapp.ui.workouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.R;
import com.evan.workoutapp.ui.workouts.data.Workout;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.Viewholder> {

    private Context context;
    private ArrayList<Workout> listOfWorkouts;

    public WorkoutAdapter(Context context, ArrayList<Workout> workoutsArrayList) {
        this.context = context;
        this.listOfWorkouts = workoutsArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Workout workout = listOfWorkouts.get(position);
        holder.workoutImageView.setImageResource(workout.getImageID());
        holder.workoutTitleTextView.setText(workout.getName());
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public int getItemCount() {
        return listOfWorkouts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView workoutImageView;
        private TextView workoutTitleTextView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            workoutImageView = itemView.findViewById(R.id.workoutImageCategory);
            workoutTitleTextView = itemView.findViewById(R.id.workoutTitleCategory);
        }

    }

}
