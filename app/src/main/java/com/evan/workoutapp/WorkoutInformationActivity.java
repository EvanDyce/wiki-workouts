package com.evan.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.data.workout.PremadeWorkouts;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.databinding.ActivityMainBinding;
import com.evan.workoutapp.databinding.ActivityWorkoutInformationBinding;

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
        ArrayList<String> exerciseNames = new ArrayList<>();
        // when returning from firestore they are stored as hashmaps
//        for (Exercises.Exercise exercise : workout.getExercisesInWorkout()) {
//            exerciseNames.add(exercise.getName());
//        }

        // three args are context, the layout ,and the data that is to be displayed
        // wrong type of id, it wants a TextView, will look into it
        // works when it displays and empty list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_workout_information, exerciseNames);

        // no need for an on change because they are all hardstuck immutable
        binding.lvExercises.setAdapter(adapter);


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
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}