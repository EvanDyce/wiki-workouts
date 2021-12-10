package com.evan.workoutapp.ui.workouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.databinding.ActivityWorkoutInformationBinding;
import com.evan.workoutapp.ui.workouts.started.StartedWorkout;
import com.evan.workoutapp.ui.workouts.started.WorkoutStartedActivity;
import com.evan.workoutapp.utils.CustomExerciseDialog;

import java.util.ArrayList;

public class WorkoutInformationActivity extends AppCompatActivity {

    private ActivityWorkoutInformationBinding binding;
    private Workout workout;
    private ArrayList<Workout> workoutArrayList;
    private int index;
    private Intent nextIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWorkoutInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();

        // get the action bar and set teh color and home button
        if (actionBar == null) Log.e("FUCKER", "IT IS NULL");
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D26466")));
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayList<Workout> workouts;
//        get the intent and retrieve the list, index, and intent to start next
        try {
            Intent intent = getIntent();
            this.workoutArrayList = (ArrayList<Workout>) intent.getExtras().get("workout_list");
            this.index = (int) intent.getExtras().get("workout_index");
            this.nextIntent = (Intent) intent.getExtras().get("next_intent");

        } catch (RuntimeException exception) {
            Log.e("WIA", "Intent data is bad");
            finish();
        }
        // gets the workout that was clicked
        workout = this.workoutArrayList.get(this.index);

        // setting all of the correct text with the new workout information
        String name = workout.getName();
        String category = workout.getCategory();
        String primary = workout.getPrimary();
        String secondary = workout.getSecondary();
        String description = workout.getDescription();
        String difficulty = workout.getDifficulty();
        String length = workout.getLength();

        binding.tvTitle.setText(name);
        binding.tvPrimaryMuscles.setText(primary);
        binding.tvSecondarMuscles.setText(secondary);
        binding.tvWorkoutDescription.setText(description);
        binding.tvWorkoutDifficulty.setText(difficulty);
        binding.tvWorkoutLength.setText(length);

        if (primary == null || primary.length() == 0) {
            binding.tvPrimaryMuscles.setText("Not Specified");
        }
        if (secondary == null || secondary.length() == 0) {
            binding.tvSecondarMuscles.setText("Not Spedified");
        }

        // arraylist of names of all exercises in the workout
        LinearLayout linearLayout = findViewById(R.id.ll_exercise_names);

        for (Exercises.Exercise exercise : workout.getExercisesInWorkout()) {
            TextView temp = new TextView(this);
            temp.setText(exercise.getName());
            temp.setId(Integer.valueOf(exercise.getId()));
            temp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            temp.setTextColor(getResources().getColor(R.color.black));
            temp.setTextSize(18.0F);
            temp.setPadding(0, 5, 0, 0);
            temp.setPaintFlags(temp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            temp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.black_circle_bullet, 0, 0, 0);
            temp.setCompoundDrawablePadding(25);
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomExerciseDialog ced = new CustomExerciseDialog(WorkoutInformationActivity.this, exercise);
                    ced.show();
                }
            });
            linearLayout.addView(temp);
        }

        binding.startWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutInformationActivity.this, WorkoutStartedActivity.class);
                intent.putExtra("workout", new StartedWorkout(workout));
                startActivity(intent);
//                CustomExerciseDialog customExerciseDialog = new CustomExerciseDialog(WorkoutInformationActivity.this, workout.getExercisesInWorkout().get(0));
//                customExerciseDialog.show();
            }
        });
    }

    /**
     * on click for the back button on action bar
     * @param item menuitem that was clicked, always back button
     * @return returns true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(this.nextIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}