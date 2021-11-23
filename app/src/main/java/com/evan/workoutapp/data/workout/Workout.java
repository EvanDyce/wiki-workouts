package com.evan.workoutapp.data.workout;

import java.io.Serializable;
import java.util.ArrayList;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises.Exercise;

public class Workout implements Serializable {
    private String name, description, category;
    private ArrayList<Exercise> exercisesInWorkout;
    private int image_id;

    public Workout(String name, String description, String category, ArrayList<Exercise> exercises) {
        this.name = name;
        this.description = description;
        this.exercisesInWorkout = exercises;
        this.category = category;

        switch (category) {
            case "Arms":
                this.image_id = R.drawable.arms_image;
                break;

            case "Abs":
                this.image_id = R.drawable.abs_image;
                break;

            case "Back":
                this.image_id = R.drawable.back_image;
                break;

            case "Calves":
                this.image_id = R.drawable.calf_image;
                break;

            case "Legs":
                this.image_id = R.drawable.legs_image;
                break;

            case "Shoulders":
                this.image_id = R.drawable.shoulder_image;
                break;

            case "Chest":
                this.image_id = R.drawable.chest_image;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + category);
        }


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

    public int getImageID() { return this.image_id; }

    public ArrayList<Exercise> getExercisesInWorkout() { return this.exercisesInWorkout; }

}
