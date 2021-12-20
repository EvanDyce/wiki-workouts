package com.evan.workoutapp.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.workout.FinishedWorkout;
import com.evan.workoutapp.data.workout.Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HistoricWorkoutAdapter extends GeneralWorkoutAdapter {
    public HistoricWorkoutAdapter(Context context, ArrayList<? extends Workout> workouts, WorkoutClickedListener listener) {
        super(context, workouts, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralViewholder holder, int position) {
        super.onBindViewHolder(holder, position);

        FinishedWorkout workout = (FinishedWorkout) this.workoutArrayList.get(position);

        ((HistoricViewholder) holder).durationTextView.setText(String.format("Duration %s", workout.getDuration()));
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.CANADA);
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.CANADA);
        String time = timeFormat.format(workout.getFinishedDate());
        String date = dmyFormat.format(workout.getFinishedDate());
        ((HistoricViewholder) holder).dateTextView.setText(String.format("Completed At: %s on %s", time, date));
    }

    @NonNull
    @Override
    public HistoricViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historic_workout_card, parent, false);
        return new HistoricViewholder(view, mWorkoutClickedListener);
    }

    public class HistoricViewholder extends GeneralViewholder {
        private TextView durationTextView;
        private TextView dateTextView;

        public HistoricViewholder(View itemView, WorkoutClickedListener workoutClickedListener) {
//            passing the view and listener to parent class to handle
            super(itemView, workoutClickedListener);

            // setting the two textviews to instance variables
            durationTextView = itemView.findViewById(R.id.durationTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
