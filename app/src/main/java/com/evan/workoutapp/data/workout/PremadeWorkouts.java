package com.evan.workoutapp.data.workout;

import java.util.ArrayList;

public class PremadeWorkouts {
    private static ArrayList<Workout> premadeWorkoutsArraylist = new ArrayList<>();

    public static ArrayList<Workout> getPremadeWorkoutsArraylist() { return premadeWorkoutsArraylist; }

    public static void addWorkoutToList(Workout workout) {
        premadeWorkoutsArraylist.add(workout);
    }
}
