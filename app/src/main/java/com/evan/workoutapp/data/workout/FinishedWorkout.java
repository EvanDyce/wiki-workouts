package com.evan.workoutapp.data.workout;

import com.evan.workoutapp.data.Exercises;

import java.util.ArrayList;
import java.util.Date;

public class FinishedWorkout extends Workout {
    private Date finishedDate;
    private String duration;

    public FinishedWorkout(Workout workout, Date date, String duration) {
        super(workout.getName(), workout.getCategory(), workout.getDifficulty(), workout.getLength(), workout.getExercisesInWorkout());

        this.finishedDate = date;
        this.duration = duration;
    }

    public FinishedWorkout(Workout workout, String duration) {
        super(workout.getName(), workout.getCategory(), workout.getDifficulty(), workout.getLength(), workout.getExercisesInWorkout());

        this.duration = duration;
        this.finishedDate = new Date();
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public String getDuration() {
        return duration;
    }
}
