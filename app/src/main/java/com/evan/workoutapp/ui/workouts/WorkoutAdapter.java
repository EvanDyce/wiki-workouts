package com.evan.workoutapp.ui.workouts;

import android.content.Context;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.data.Workout;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.Viewholder> {

    private Context context;
    private ArrayList<Workout> listOfWorkouts;

    public WorkoutAdapter(Context context, ArrayList<Workout> workoutsArrayList) {
        this.context = context;
        this.listOfWorkouts = workoutsArrayList;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView workoutImageView;
        private exerciseTitleTextView;
        
    }

}
