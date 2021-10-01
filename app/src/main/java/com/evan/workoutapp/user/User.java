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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getWorkouts_completed() {
        return workouts_completed;
    }

    public ArrayList<Workouts> getUserWorkouts() {
        return userWorkouts;
    }
}
