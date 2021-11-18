package com.evan.workoutapp;

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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.data.workout.PremadeWorkouts;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.databinding.ActivityMainBinding;
import com.evan.workoutapp.databinding.ActivityWorkoutInformationBinding;
import com.evan.workoutapp.utils.CustomExerciseDialog;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutInformationActivity extends AppCompatActivity {

    private ActivityWorkoutInformationBinding binding;

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

        // get intent so I can get teh index that was passed
        Intent intent = getIntent();
        int index = (int) intent.getExtras().get("workout_index");
        // gets the workout that was clicked
        Workout workout = PremadeWorkouts.getPremadeWorkoutsArraylist().get(index);

        // setting all of the correct text with the new workout information
        String name = workout.getName();
        String category = workout.getCategory();
        String description = workout.getDescription();

        binding.tvTitle.setText(name);
        binding.tvPrimaryMuscles.setText(category);
        binding.tvWorkoutDescription.setText(description);

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
                CustomExerciseDialog customExerciseDialog = new CustomExerciseDialog(WorkoutInformationActivity.this, workout.getExercisesInWorkout().get(0));
                customExerciseDialog.show();
            }
        });
        Toast.makeText(this, "Workout: " + PremadeWorkouts.getPremadeWorkoutsArraylist().get(index).getName(), Toast.LENGTH_SHORT).show();
    }

    /**
     * on click for the back button on action bar
     * @param item menuitem that was clicked, always back button
     * @return returns true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("fragment", "workouts");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}