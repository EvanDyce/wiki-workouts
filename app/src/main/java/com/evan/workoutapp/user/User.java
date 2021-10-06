package com.evan.workoutapp.user;

import java.util.ArrayList;

public class User {
    private String name, email;
    private int workouts_completed;
    private ArrayList<Workouts> userWorkouts;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.workouts_completed = 0;
        this.userWorkouts = new ArrayList<>();
    }

    public User(String name, String email, ArrayList<Workouts> workouts, int workoutsCompleted) {
        this.name = name;
        this.email = email;
        this.userWorkouts = workouts;
        this.workouts_completed = workoutsCompleted;
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

    public ArrayList<Workouts> getUserWorkouts() {
        return userWorkouts;
    }
}
