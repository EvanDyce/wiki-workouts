package com.evan.workoutapp.data.workout;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises.Exercise;

public class Workout implements Serializable {
    private String name = "", description= "", category= "", primary= "", secondary= "", length= "", difficulty = "";
    private ArrayList<Exercise> exercisesInWorkout;

    public Workout(String name, String category, String primary, String secondary,
                   String description, String difficulty, String length, ArrayList<Exercise> exercises) {
        this.name = name;
        this.category = category;
        this.primary = primary;
        this.secondary = secondary;
        this.description = description;
        this.difficulty = difficulty;
        this.length = length;
        this.exercisesInWorkout = exercises;
    }

    public String getLength() { return this.length; }

    public String getDifficulty() { return this.difficulty; }

    public String getPrimary() {
        return this.primary;
    }

    public String getSecondary() {
        return this.secondary;
    }

    public String getCategory() { return this.category; }

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

    public int getImageID() {
        int image_id;
        switch (category) {
            case "Arms":
                image_id = R.drawable.arms_image;
                break;

            case "Abs":
                image_id = R.drawable.abs_image;
                break;

            case "Back":
                image_id = R.drawable.back_image;
                break;

            case "Calves":
                image_id = R.drawable.calf_image;
                break;

            case "Legs":
                image_id = R.drawable.legs_image;
                break;

            case "Shoulders":
                image_id = R.drawable.shoulder_image;
                break;

            case "Chest":
                image_id = R.drawable.chest_image;
                break;

            default:
                Log.e("FUCKEd", category);
                throw new IllegalStateException("Unexpected value: " + category);
        }
        return image_id;
    }

    public ArrayList<Exercise> getExercisesInWorkout() { return this.exercisesInWorkout; }

}
