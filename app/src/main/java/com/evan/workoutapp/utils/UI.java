package com.evan.workoutapp.utils;

import android.app.Activity;
import android.content.Intent;

import com.evan.workoutapp.MainActivity;
import com.google.firebase.auth.FirebaseUser;

public class UI {
    public static void updateUI(Activity activity, FirebaseUser user) {
        if(user == null) { return; }

        activity.startActivity(new Intent(activity, MainActivity.class));
    }
}
