package com.evan.workoutapp.ui.workouts.started;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.data.FirestoreFunctions;
import com.evan.workoutapp.data.workout.FinishedWorkout;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.databinding.ActivityWorkoutInformationBinding;
import com.evan.workoutapp.databinding.ActivityWorkoutStartedBinding;
import com.evan.workoutapp.user.CurrentUserSingleton;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WorkoutStartedActivity extends AppCompatActivity {

    private ActivityWorkoutStartedBinding binding;
    private Intent returnIntent;

    private Workout workout;
    private Date start_date;
    private ArrayList<Exercises.Exercise> exerciseArrayList;
    private int exerciseCardIndex = 0;
    private String timeTracker = "";

    private int seconds;
    private boolean is_running, was_running;

    private int restTimerSeconds = 60;
    private boolean isResting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkoutStartedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();

        // get the action bar and set teh color and home button
        if (actionBar == null) Log.e("FUCKER", "IT IS NULL");
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D26466")));
        actionBar.setDisplayHomeAsUpEnabled(true);

        start_date = new Date();

        // getting data passed through intent
        returnIntent = (Intent) getIntent().getExtras().get("return_intent");
        workout = (Workout) getIntent().getExtras().get("workout");
        exerciseArrayList = workout.getExercisesInWorkout();
        updateExerciseCard();

        binding.tvTitle.setText(workout.getName());

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (exerciseCardIndex + 1) % exerciseArrayList.size();
                exerciseCardIndex = index;
                updateExerciseCard();
            }
        });

        binding.buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = exerciseCardIndex - 1;
                if (index < 0) {
                    index = exerciseArrayList.size() - 1;
                }

                exerciseCardIndex = index;
                updateExerciseCard();
            }
        });

        binding.buttonCancelWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(returnIntent);
            }
        });

        binding.buttonFinishWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_running = !is_running;
                String duration = timeTracker;
                FinishedWorkout finishedWorkout = new FinishedWorkout(workout, duration);
                CurrentUserSingleton.getInstance().getFinishedWorkouts().add(finishedWorkout);
                FirestoreFunctions.updateUserData();
                startActivity(returnIntent);
            }
        });

        binding.restButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isResting) {
                    isResting = true;
                    startRestTimer();
                }
            }
        });

        is_running = true;

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            is_running = savedInstanceState.getBoolean("isRunning");
            was_running = savedInstanceState.getBoolean("wasRunning");
        }

        startTimer();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", is_running);
        outState.putBoolean("wasRunning", was_running);
    }

    @Override
    protected void onPause() {
        super.onPause();
        was_running = is_running;
        is_running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (was_running) {
            is_running = true;
        }
    }

    private void startRestTimer() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isResting) {
                    int secs = restTimerSeconds % 60;

                    String time = String.format(Locale.CANADA, "    %02d    ",
                            secs);
                    binding.restButton.setText(time);
                    restTimerSeconds--;
                    handler.postDelayed(this, 1000);
                }

                if (restTimerSeconds == 0) {
                    isResting = false;
                    restTimerSeconds = 60;
                    binding.restButton.setText("REST");
                }

            }
        });
    }

    private void updateExerciseCard() {
        Exercises.Exercise exercise = exerciseArrayList.get(exerciseCardIndex);

        binding.exerciseCard.exerciseCardImage.setImageResource(exercise.getImageID());
        binding.exerciseCard.exerciseTitle.setText(exercise.getName());
        binding.exerciseCard.exerciseDescription.setText(exercise.getDescription());
    }

    private void startTimer() {
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int mins = seconds / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.CANADA, "    %02d:%02d    ",
                        mins, secs);

                if (is_running) {
                    seconds++;
                }
                timeTracker = time;
                handler.postDelayed(this, 1000);
            }
        });
    }
}