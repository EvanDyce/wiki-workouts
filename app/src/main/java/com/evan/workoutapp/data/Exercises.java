package com.evan.workoutapp.data;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.evan.workoutapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Exercises implements Serializable {
    /**
     * Exercise Class for each individual exercise
     */
    public static class Exercise implements Serializable{
        private final String id, name, description, category;
        private final String equipment;

        public Exercise(String id, String name, String description, String category, String equipment) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.category = category;
            this.equipment = equipment;
        }

        public Exercise(String id, String name, String description, String category, ArrayList<String> equipment) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.category = category;
            String cat1 = "Equipment: ";
            for (String equip : equipment) {
                cat1 += equip + ", ";
            }
            this.equipment = cat1;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCategory() {
            return category;
        }

        public String getEquipment() {
            return equipment;
        }

        public int getImageID() {
            int image;

            switch (category) {
                case "Arms":
                    image = R.drawable.arms_image;
                    break;

                case "Abs":
                    image = R.drawable.abs_image;
                    break;

                case "Back":
                    image = R.drawable.back_image;
                    break;

                case "Calves":
                    image = R.drawable.calf_image;
                    break;

                case "Legs":
                    image = R.drawable.legs_image;
                    break;

                case "Shoulders":
                    image = R.drawable.shoulder_image;
                    break;

                case "Chest":
                    image = R.drawable.chest_image;
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + category);
            }
            return image;
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

//                    Exercises.addExercise(new Exercise(id, name, description, category, equipment, 10));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, ArrayList<Exercise>> getMap() {
        return map;
    }

    public static ArrayList<Exercise> getAllExercises() {
        ArrayList<Exercise> temp = new ArrayList<>();

        for (String key : map.keySet()) {
            temp.addAll(Objects.requireNonNull(map.get(key)));
        }

        Log.e("CHECKING", String.valueOf(temp.size()));
        Collections.shuffle(temp);
        return temp;
    }

}
