package com.evan.workoutapp.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.evan.workoutapp.LoginActivity;
import com.evan.workoutapp.R;
import com.evan.workoutapp.data.workout.PremadeWorkouts;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.user.CurrentUserSingleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreFunctions {

    public interface FirestoreCallback {
        void dataRetrieved();
        void dataRetrievalFailed();
    }

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "FIRESTORE";

    public static void testingData(Context context) {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FIRESTORE", document.getString("name"));
                                Toast.makeText(context, document.getString("name"), Toast.LENGTH_SHORT).show();
                            }
                        }
                     }
                });
    }

    /**
     * Retrieves all the exercises from firebase database
     * Loads into the full exercises list and into the category specific list
     */
    public static void retrieveExercisesFromFirestore(FirestoreCallback callback) {

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        if (Exercises.getAllExercises().size() != 0) {
            CurrentUserSingleton.getInstance();
            callback.dataRetrieved();
            return;
        }

        db.collection("exercise")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            CurrentUserSingleton.getInstance();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId());

                                String id = document.getId();

                                String name = document.getString("name");

                                String description = document.getString("description");
                                if (description == null || description.length() == 0) {
                                    description = "No Description Available for this Exercise.";
                                }

                                String category = document.getString("category");
                                ArrayList<String> equipment = (ArrayList<String>) document.get("equipment");
                                if (equipment.size() == 0) {
                                    equipment.add("No Equipment");
                                }

                                Exercises.Exercise exercise = new Exercises.Exercise(id, name, description, category, equipment);
                                Exercises.addExercise(exercise);
                            }
                            retrieveWorkoutsFromFirestore(db, callback);
//                            callback.dataRetrieved();
                        } else {
                            callback.dataRetrievalFailed();
                        }
                     }
                });
    }

    /**
     * function that retrieves the workouts from firestore and loads them into PremadeWorkouts.java
     * @param db firestore reference for request
     * @param callback callback for async response
     */
    public static void retrieveWorkoutsFromFirestore(FirebaseFirestore db, FirestoreCallback callback) {
        db.collection("workouts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d(TAG, documentSnapshot.getId() + " retrieved");

                                String name = documentSnapshot.getString("name");
                                String category = documentSnapshot.getString("category");
                                String primary = documentSnapshot.getString("primary");
                                String secondary = documentSnapshot.getString("secondary");
                                String description = documentSnapshot.getString("description");
                                String difficulty = documentSnapshot.getString("difficulty");
                                String length = documentSnapshot.getString("length");
                                ArrayList<HashMap<String, Object>> mapList = (ArrayList<HashMap<String, Object>>) documentSnapshot.get("exercises");
                                ArrayList<Exercises.Exercise> exercises = new ArrayList<>();
                                for (HashMap<String, Object> map : mapList) {
                                    String exerciseName = (String) map.get("name");
                                    String exerciseDescription = (String) map.get("description");
                                    String equipment = (String) map.get("equipment");
                                    String id = (String) map.get("id");
                                    String exerciseCategory = (String) map.get("category");
                                    assert equipment != null;
                                    exercises.add(new Exercises.Exercise(id, exerciseName, exerciseDescription, exerciseCategory, equipment));
                                }
//                                ArrayList<Exercises.Exercise> exerciseArrayList = (ArrayList<Exercises.Exercise>) documentSnapshot.get("exercises");

                                PremadeWorkouts.addWorkoutToList(new Workout(name, category, primary, secondary,
                                        description, difficulty, length, exercises));
//                                PremadeWorkouts.addWorkoutToList(new Workout(name, description, category, exercises));
                            }
                            callback.dataRetrieved();
                        } else {
                            Log.e("FUCKER", "WORKOUT RETRIEVAL FAIL");
                        }
                    }
                });
    }

    public static void updateUserData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put("email",CurrentUserSingleton.getInstance().getEmail());
        data.put("name", CurrentUserSingleton.getInstance().getName());
        data.put("workouts_completed", CurrentUserSingleton.getInstance().getWorkoutsCompleted());

        // create a list of maps with each map being a workout
        ArrayList<HashMap<String, Object>> workouts = new ArrayList<>();
        for (Workout workout : CurrentUserSingleton.getInstance().getUserWorkouts()) {
            HashMap<String, Object> obj = new HashMap<>();
            obj.put("name", workout.getName());
            obj.put("category", workout.getCategory());
            obj.put("primary", workout.getPrimary());
            obj.put("secondary", workout.getSecondary());
            obj.put("description", workout.getDescription());
            obj.put("difficulty", workout.getDifficulty());
            obj.put("length", workout.getLength());

            // new list of maps for each exercise
            ArrayList<HashMap<String, Object>> exercises = new ArrayList<>();
            for (Exercises.Exercise exercise : workout.getExercisesInWorkout()) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", exercise.getId());
                map.put("name", exercise.getName());
                map.put("description", exercise.getDescription());
                map.put("category", exercise.getCategory());
                map.put("equipment", exercise.getEquipment());
                exercises.add(map);
            }
            // adding exericse list to workout map
            obj.put("exercises", exercises);
            workouts.add(obj);
        }
        data.put("custom_workouts", workouts);

        db.collection("users")
                .document(CurrentUserSingleton.getInstance().getEmail())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User data updated success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "User data not updated successfully");
                    }
                });
    }

    public static void loadExercisesIntoFirestore(HashMap<String, ArrayList<Exercises.Exercise>> map) {
        for (String category : map.keySet()) {
            for (Exercises.Exercise exercise : map.get(category)) {
                Map<String, Object> data = new HashMap<>();
                data.put("id", exercise.getId());
                data.put("name", exercise.getName());
                data.put("description", exercise.getDescription());
                data.put("category", exercise.getCategory());
                data.put("equipment", exercise.getEquipment());

                db.collection("exercise")
                        .document(Integer.valueOf(exercise.getId()).toString())
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Error writing document", e);
                            }
                        });
            }
        }
    }




}
