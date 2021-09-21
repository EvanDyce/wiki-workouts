package com.evan.workoutapp.data;

import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercises {
    /**
     * Exercise Class for each individual exercise
     */
    public static class Exercise {
        private int id;
        private String name, descirption, category;
        private ArrayList<String> equipment;

        public Exercise(int id, String name, String descirption, String category, ArrayList<String> equipment) {
            this.id = id;
            this.name = name;
            this.descirption = descirption;
            this.category = category;
            this.equipment = equipment;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescirption() {
            return descirption;
        }

        public String getCategory() {
            return category;
        }

        public ArrayList<String> getEquipment() {
            return equipment;
        }
    }

    private static Map<String, ArrayList<Exercise>> map = new HashMap<>();
    private static ArrayList<Exercise> legExercises = new ArrayList<>();
    private static ArrayList<Exercise> chestExercises = new ArrayList<>();
    private static ArrayList<Exercise> abExercises = new ArrayList<>();
    private static ArrayList<Exercise> shoulderExercises = new ArrayList<>();
    private static ArrayList<Exercise> armExercises = new ArrayList<>();
    private static ArrayList<Exercise> backExercises = new ArrayList<>();
    private static ArrayList<Exercise> calfExercises = new ArrayList<>();


    /**
     * Adds exercise to correct list in hashmap
     * @param e exercise to be added
     */
    public static void addExercise(@NonNull Exercise e) {
        ArrayList<Exercise> temp;
        if (map.containsKey(e.getCategory())) {
            temp = map.get(e.getCategory());
        } else {
            temp = new ArrayList<>();
        }
        temp.add(e);
        map.put(e.getCategory(), temp);
        addToCategoryExerciseList(e);
    }

    /**
     * Extracts all data from raw JSON request and turns into exercises to be added to the list
     * @param arrayList list of JSONObjects returned from API query
     */
    public static void getDataFromObjects(ArrayList<JSONObject> arrayList) {
        for (JSONObject obj : arrayList) {
            try {
                JSONArray arr = obj.getJSONArray("results");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject exercise = arr.getJSONObject(i);

                    int id = (int) exercise.getLong("id");
                    String name = exercise.getString("name");
                    String description = exercise.getString("description").replaceAll("<[^>]*>", "");

                    JSONObject cat = exercise.getJSONObject("category");
                    String category = cat.getString("name");

                    JSONArray equip = exercise.getJSONArray("equipment");
                    ArrayList<String> equipment = new ArrayList<>();
                    for (int j = 0; j < equip.length(); j++) {
                        JSONObject temp = (JSONObject) equip.get(j);
                        equipment.add(temp.getString("name"));
                    }

                    Exercises.addExercise(new Exercise(id, name, description, category, equipment));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, ArrayList<Exercise>> getMap() {
        return map;
    }

    private static void addToCategoryExerciseList(Exercise exercise) {
        switch (exercise.getCategory()) {
            case "Legs":
                legExercises.add(exercise);
            case "Chest":
                chestExercises.add(exercise);
            case "Abs":
                abExercises.add(exercise);
            case "Shoulders":
                shoulderExercises.add(exercise);
            case "Arms":
                armExercises.add(exercise);
            case "Back":
                backExercises.add(exercise);
            case "Calves":
                calfExercises.add(exercise);
        }
    }
    public static ArrayList<Exercise> getLegExercises() {
        return legExercises;
    }

    public static ArrayList<Exercise> getChestExercises() {
        return chestExercises;
    }

    public static ArrayList<Exercise> getAbExercises() {
        return abExercises;
    }

    public static ArrayList<Exercise> getShoulderExercises() {
        return shoulderExercises;
    }

    public static ArrayList<Exercise> getArmExercises() {
        return armExercises;
    }

    public static ArrayList<Exercise> getBackExercises() {
        return backExercises;
    }

    public static ArrayList<Exercise> getCalfExercises() {
        return calfExercises;
    }
}
