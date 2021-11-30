package com.evan.workoutapp.ui;

import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.evan.workoutapp.data.Exercises;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public interface ExerciseFilteringInterface {
    default void filter(String s, String categoryFilter,
                        ArrayList<Exercises.Exercise> exercises, RecyclerView.Adapter adapter) {
        // setting the new list to one with all teh exercises
        // removes any that don't contain the new things.
        // may be slow, but we'll see
        ArrayList<Exercises.Exercise> newList = new ArrayList<>();
        Map<String, ArrayList<Exercises.Exercise>> map = Exercises.getMap();
        switch (categoryFilter) {
            case "Chest":
                newList.addAll(Objects.requireNonNull(map.get("Chest")));
                break;

            case "Shoulders":
                newList.addAll(Objects.requireNonNull(map.get("Shoulders")));
                break;

            case "Legs":
                newList.addAll(Objects.requireNonNull(map.get("Legs")));
                break;

            case "Calves":
                newList.addAll(Objects.requireNonNull(map.get("Calves")));
                break;

            case "Back":
                newList.addAll(Objects.requireNonNull(map.get("Back")));
                break;

            case "Arms":
                newList.addAll(Objects.requireNonNull(map.get("Arms")));
                break;

            case "Abs":
                newList.addAll(Objects.requireNonNull(map.get("Abs")));
                break;

            default:
                newList.addAll(Objects.requireNonNull(Exercises.getAllExercises()));
        }

        for (int i = 0; i < newList.size(); i++) {
            if (!newList.get(i).getName().toLowerCase(Locale.ROOT).contains(s)) {
                newList.remove(i);
                i--;
            }
        }
        exercises.clear();
        exercises.addAll(newList);
        adapter.notifyDataSetChanged();
    }

    default void filterByCategory(String s, ArrayList<Exercises.Exercise> exerciseArrayList, RecyclerView.Adapter adapter) {
        if (s.equals("All")) {
            exerciseArrayList.clear();
            ArrayList<Exercises.Exercise> newList = Exercises.getAllExercises();
            exerciseArrayList.addAll(newList);
            adapter.notifyDataSetChanged();
            return;
        }
        exerciseArrayList.clear();
        ArrayList<Exercises.Exercise> newList = Exercises.getMap().get(s);
        assert newList != null;
        exerciseArrayList.addAll(newList);
        adapter.notifyDataSetChanged();
    }
}
