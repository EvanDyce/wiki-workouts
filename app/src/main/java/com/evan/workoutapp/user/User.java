package com.evan.workoutapp.user;

import androidx.annotation.NonNull;

import com.evan.workoutapp.data.workout.FinishedWorkout;
import com.evan.workoutapp.data.workout.Workout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class User {
    private String name, email;
    private int workouts_completed;
    private ArrayList<Workout> userWorkouts;
    private ArrayList<Workout> finishedWorkouts;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.workouts_completed = 0;
        this.userWorkouts = new ArrayList<>();
        this.finishedWorkouts = new ArrayList<>();
    }

    public User(String name, String email, ArrayList<Workout> workouts,
                int workoutsCompleted, ArrayList<Workout> finishedWorkouts) {
        this.name = name;
        this.email = email;
        this.userWorkouts = workouts;
        this.workouts_completed = workoutsCompleted;
        this.finishedWorkouts = finishedWorkouts;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getWorkoutsCompleted() {
        return workouts_completed;
    }

    public ArrayList<Workout> getUserWorkouts() {
        return userWorkouts;
    }

    public void addUserCustomWorkout(Workout workout) { this.userWorkouts.add(workout); }

    public ArrayList<Workout> getFinishedWorkouts() { return this.finishedWorkouts; }

    @NonNull
    @Override
    public String toString() {
        return this.getName() + " is user with email " + this.getEmail() + ". They have completed " + this.getWorkoutsCompleted() + " workouts";
    }

}
