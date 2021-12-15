package com.evan.workoutapp.user;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.data.workout.FinishedWorkout;
import com.evan.workoutapp.data.workout.Workout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CurrentUserSingleton {
    private final static String TAG = "USER SINGLETON";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static User instance;


    public static synchronized User getInstance() {
        if (instance == null) {
            FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();
            if (current == null) return null;
            String email = current.getEmail();
            if (email == null) return null;

            db.collection("users")
                    .document(email).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Map<String, Object> map = document.getData();
                                    String email = (String) map.get("email");
                                    String name = (String) map.get("name");
                                    int workouts_completed = ((Long) map.get("workouts_completed")).intValue();
                                    ArrayList<Workout> workouts = new ArrayList<>();

                                    // retrieving and adding all the custom workouts
                                    ArrayList<HashMap<String, Object>> mapList = (ArrayList<HashMap<String, Object>>) document.get("custom_workouts");
                                    for (HashMap<String, Object> mappy : mapList) {
                                        String category = (String) mappy.get("category");
                                        String description = (String) mappy.get("description");
                                        String workout_name = (String) mappy.get("name");
                                        String primary = (String) mappy.get("primary");
                                        String secondary = (String) mappy.get("secondary");
                                        String difficulty = (String) mappy.get("difficulty");
                                        String length = (String) mappy.get("length");
                                        ArrayList<Exercises.Exercise> exerciseArrayList = new ArrayList<>();

                                        // retrieve the arraylist of map exercises and then create teh individual exercises to add
                                        ArrayList<HashMap<String, Object>> exercises = (ArrayList<HashMap<String, Object>>) mappy.getOrDefault("exercises", new ArrayList<>());
                                        for (HashMap<String, Object> exercise : exercises) {
                                            String exercise_id = (String) exercise.get("id");
                                            String exercise_name = (String) exercise.get("name");
                                            String exercise_description = (String) exercise.get("description");
                                            String exercise_category = (String) exercise.get("category");
                                            String exercise_equipment = (String) exercise.get("equipment");
                                            exerciseArrayList.add(new Exercises.Exercise(exercise_id, exercise_name, exercise_description,
                                                    exercise_category, exercise_equipment));
                                        }
                                        workouts.add(new Workout(workout_name, category, primary, secondary, description,
                                                difficulty, length, exerciseArrayList));
                                    }


                                    ArrayList<FinishedWorkout> finishedWorkouts = new ArrayList<>();
                                    // getting the stuff for the finished workouts
                                    ArrayList<HashMap<String, Object>> mapArrayList = (ArrayList<HashMap<String, Object>>) document.get("finished_workouts");
                                    assert mapArrayList != null;
                                    for (HashMap<String, Object> mappy : mapArrayList) {
                                        String category = (String) mappy.get("category");
                                        String description = (String) mappy.get("description");
                                        String workout_name = (String) mappy.get("name");
                                        String primary = (String) mappy.get("primary");
                                        String secondary = (String) mappy.get("secondary");
                                        String difficulty = (String) mappy.get("difficulty");
                                        String length = (String) mappy.get("length");

                                        String duration = (String) mappy.get("duration");
                                        Date date = ((Timestamp) mappy.get("date")).toDate();

                                        ArrayList<Exercises.Exercise> exerciseArrayList = new ArrayList<>();

                                        // retrieve the arraylist of map exercises and then create teh individual exercises to add
                                        ArrayList<HashMap<String, Object>> exercises = (ArrayList<HashMap<String, Object>>) mappy.getOrDefault("exercises", new ArrayList<>());
                                        for (HashMap<String, Object> exercise : exercises) {
                                            String exercise_id = (String) exercise.get("id");
                                            String exercise_name = (String) exercise.get("name");
                                            String exercise_description = (String) exercise.get("description");
                                            String exercise_category = (String) exercise.get("category");
                                            String exercise_equipment = (String) exercise.get("equipment");
                                            exerciseArrayList.add(new Exercises.Exercise(exercise_id, exercise_name, exercise_description,
                                                    exercise_category, exercise_equipment));
                                        }
                                        finishedWorkouts.add(new FinishedWorkout(new Workout(workout_name, category, primary, secondary, description,
                                                difficulty, length, exerciseArrayList), date, duration));
                                    }

                                    instance = new User(name, email, workouts, workouts_completed, finishedWorkouts);
                                } else {
                                    Log.e(TAG, "Document doesn't exist");
                                }
                            } else {
                                Log.e(TAG, "Google's fault");
                            }
                        }
                    });
        }
        return instance;
    }

    public static void signOutCurrentUser() {
        CurrentUserSingleton.instance = null;
    }

    public static void setCurrentUser(User user) { CurrentUserSingleton.instance = user; }

}
