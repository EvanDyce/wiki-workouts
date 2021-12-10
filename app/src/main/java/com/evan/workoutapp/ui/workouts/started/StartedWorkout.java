package com.evan.workoutapp.ui.workouts.started;

import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.data.workout.Workout;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class StartedWorkout extends Workout implements Serializable {

    private Date startedDate;

    public StartedWorkout(Workout workout) {
        super(workout.getName(), workout.getCategory(), workout.getPrimary(), workout.getSecondary(), workout.getDescription(),
                workout.getDifficulty(), workout.getLength(), workout.getExercisesInWorkout());

        this.startedDate = new Date();
    }

    public StartedWorkout(String name, String category, String primary, String secondary, String desc,
                          String difficulty, String length, ArrayList<Exercises.Exercise> exercises) {
        super(name, category, primary, secondary, desc, difficulty, length, exercises);

        this.startedDate = new Date();
    }

}
