package com.evan.workoutapp.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirestoreFunctions {
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
    public static void retrieveExercisesFromFirestore() {
        db.collection("exercise")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId());

                                String id = document.getId();
                                String name = document.getString("name");
                                String description = document.getString("description");
                                String category = document.getString("category");
                                ArrayList<String> equipment = (ArrayList<String>) document.get("equipment");

                                Exercises.Exercise exercise = new Exercises.Exercise(id, name, description, category, equipment);
                                Exercises.addExercise(exercise);

                            }
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
                data.put("description", exercise.getDescirption());
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
