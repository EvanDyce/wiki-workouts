package com.evan.workoutapp.data;

import android.util.Log;
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
        private final String id, name, descirption, category;
        private final ArrayList<String> equipment;

        public Exercise(String id, String name, String descirption, String category, ArrayList<String> equipment) {
            this.id = id;
            this.name = name;
            this.descirption = descirption;
            this.category = category;
            this.equipment = equipment;
        }

        public String getId() {
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

    private static final Map<String, ArrayList<Exercise>> map = new HashMap<>();
    private static final String TAG = "EXERCISES";


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

                    String id = ((Long)exercise.getLong("id")).toString();
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

    public static ArrayList<Exercise> getLegExercises() {
        return map.get("Legs");
    }

    public static ArrayList<Exercise> getChestExercises() {
        return map.get("Chest");
    }

    public static ArrayList<Exercise> getAbExercises() {
        return map.get("Abs");
    }

    public static ArrayList<Exercise> getShoulderExercises() {
        return map.get("Shoulders");
    }

    public static ArrayList<Exercise> getArmExercises() {
        return map.get("Arms");
    }

    public static ArrayList<Exercise> getBackExercises() {
        return map.get("Back");
    }

    public static ArrayList<Exercise> getCalfExercises() {
        return map.get("Calves");
    }
}
