package com.evan.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.databinding.ActivityExerciseCategoriesBinding;

public class ExerciseCategoriesActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView categoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_categories);

        // get teh views in layout
        categoryTitle = findViewById(R.id.exerciseCategoryName);
        linearLayout = findViewById(R.id.exercise_linear_layout);

        // get category name that was passed through intent
        String category = getIntent().getExtras().getString("category");

        for (Exercises.Exercise exercise : Exercises.getMap().get(category)) {
            TextView temp = new TextView(ExerciseCategoriesActivity.this);
            temp.setText(exercise.getName());
            temp.setTextColor(getResources().getColor(R.color.salmon));
            linearLayout.addView(temp);
        }

    }
}