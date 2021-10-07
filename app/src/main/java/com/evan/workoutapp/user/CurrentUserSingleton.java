package com.evan.workoutapp.user;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class CurrentUserSingleton {
    private final static String TAG = "USER SINGLETON";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static User instance;


    public static synchronized User getInstance() {
        if (instance == null) {
            FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();
            assert current != null;
            String email = current.getEmail();
            assert email != null;

            db.collection("users")
                    .document(email).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Map<String, Object> map = document.getData();
                                    String email = (String) map.get("email");
                                    String name = (String) map.get("name");
                                    int workouts_completed = ((Long) map.get("workouts_completed")).intValue();
                                    ArrayList<Workouts> workouts = (ArrayList<Workouts>) map.get("custom_workouts");
                                    instance = new User(email, name, workouts, workouts_completed);
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
}
