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
import com.evan.workoutapp.data.workout.Workout;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.Viewholder> {

    private Context context;
    private ArrayList<Workout> listOfWorkouts;
    private WorkoutClickedListener mWorkoutlickedListenter;

    public WorkoutAdapter(Context context, ArrayList<Workout> workoutsArrayList, WorkoutClickedListener workoutClickedListener) {
        this.context = context;
        this.listOfWorkouts = workoutsArrayList;
        this.mWorkoutlickedListenter = workoutClickedListener;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view, mWorkoutlickedListenter);
    }

    @Override
    public int getItemCount() {
        return listOfWorkouts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        private ImageView workoutImageView;
        private TextView workoutTitleTextView;
        WorkoutClickedListener workoutClickedListener;

        public Viewholder(@NonNull View itemView, WorkoutClickedListener workoutClickedListener) {
            super(itemView);
            workoutImageView = itemView.findViewById(R.id.workoutImageCategory);
            workoutTitleTextView = itemView.findViewById(R.id.workoutTitleCategory);

            this.workoutClickedListener = workoutClickedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.workoutClickedListener.onWorkoutClicked(getAdapterPosition());
        }
    }

    public interface WorkoutClickedListener{
        void onWorkoutClicked(int position);
    }

}
