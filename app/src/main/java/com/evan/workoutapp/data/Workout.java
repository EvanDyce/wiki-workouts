package com.evan.workoutapp.data;

import java.util.ArrayList;
import com.evan.workoutapp.data.Exercises.Exercise;

public class Workout {
    private String name, description;
    private ArrayList<Exercise> exercisesInWorkout;

    public Workout(String name, String description, ArrayList<Exercise> exercises) {
        this.name = name;
        this.description = description;
        this.exercisesInWorkout = exercises;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void addExercise(Exercise exercise)  {
        exercisesInWorkout.add(exercise);
    }

    public void removeExercise(Exercise e) {
        exercisesInWorkout.remove(e);
    }

    public int workoutLength() {
        return this.exercisesInWorkout.size();
    }
}
