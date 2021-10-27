package com.evan.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.evan.workoutapp.data.workout.PremadeWorkouts;

public class WorkoutInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_information);

        // get intent so I can get teh index that was passed
        Intent intent = getIntent();
        int index = (int) intent.getExtras().get("workout_index");
        Toast.makeText(this, "Workout: " + PremadeWorkouts.getPremadeWorkoutsArraylist().get(index).getName(), Toast.LENGTH_SHORT).show();
    }
}