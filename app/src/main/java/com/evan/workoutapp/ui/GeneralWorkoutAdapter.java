package com.evan.workoutapp.ui;

import android.content.Context;
import android.util.Log;
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

public class GeneralWorkoutAdapter extends RecyclerView.Adapter<GeneralWorkoutAdapter.GeneralViewholder> {
    protected Context context;
    protected ArrayList<? extends Workout> workoutArrayList;
    protected WorkoutClickedListener mWorkoutClickedListener;

    public GeneralWorkoutAdapter(Context context, ArrayList<? extends Workout> workouts, WorkoutClickedListener listener) {
        this.context = context;
        this.workoutArrayList = workouts;
        this.mWorkoutClickedListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralViewholder holder, int position) {
        Workout workout = this.workoutArrayList.get(position);
        holder.workoutImageView.setImageResource(workout.getImageID());
        holder.workoutTitleTextView.setText(workout.getName());
    }

    @Override
    public int getItemCount() {
        return this.workoutArrayList.size();
    }

    @NonNull
    @Override
    public GeneralViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new GeneralViewholder(view, mWorkoutClickedListener);
    }

    public class GeneralViewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView workoutImageView;
        private TextView workoutTitleTextView;
        WorkoutClickedListener workoutClickedListener;

        public GeneralViewholder(View itemView, WorkoutClickedListener workoutClickedListener) {
            super(itemView);
            workoutImageView = itemView.findViewById(R.id.workoutImageCategory);
            workoutTitleTextView = itemView.findViewById(R.id.workoutTitleCategory);

            this.workoutClickedListener = workoutClickedListener;
            itemView.setOnClickListener(this::onClick);

            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this::onLongClick);
        }

        @Override
        public void onClick(View view) {
            this.workoutClickedListener.onWorkoutClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            this.workoutClickedListener.onWorkoutLongClicked(getAdapterPosition());
            Log.e("WORKOUT", "Long clicked");
            return true;
        };
    }

    public interface WorkoutClickedListener {
        void onWorkoutClicked(int position);
        void onWorkoutLongClicked(int position);
    }
}
