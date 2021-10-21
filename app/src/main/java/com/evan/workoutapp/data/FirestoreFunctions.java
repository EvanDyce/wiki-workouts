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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
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

        if (Exercises.getAllExercises().size() != 0) {
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
                                    equipment.add("No Equipment Information");
                                }

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

                                Exercises.Exercise exercise = new Exercises.Exercise(id, name, description, category, equipment, image);
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
                                String description = documentSnapshot.getString("description");
                                ArrayList<Exercises.Exercise> exerciseArrayList = (ArrayList<Exercises.Exercise>) documentSnapshot.get("exercises");

                                PremadeWorkouts.addWorkoutToList(new Workout(name, description, category, exerciseArrayList));
                            }
                            callback.dataRetrieved();
                        } else {
                            Log.e("FUCKER", "WORKOUT RETRIEVAL FAIL");
                        }
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
