package com.evan.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evan.workoutapp.data.Exercises;

import org.w3c.dom.Text;

public class ExerciseCategoriesActivity extends AppCompatActivity {

    private LinearLayout rootLinearLayout;
    private TextView categoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_categories);

        // get teh views in layout
        categoryTitle = findViewById(R.id.exerciseCategoryName);
        rootLinearLayout = findViewById(R.id.exercise_linear_layout);

        // get category name that was passed through intent
        String category = getIntent().getExtras().getString("category");

        // may have to make traditional for loop to keep index value for onClick
        for (Exercises.Exercise exercise : Exercises.getMap().get(category)) {
            TextView temp = new TextView(ExerciseCategoriesActivity.this);
            temp.setText(exercise.getName());
            temp.setTextColor(getColor(R.color.black));
            temp.setTextSize(22.0f);
            temp.setPadding(0, 0, 0, 10);

            // setting the drawable bullet point to the left of exercises
            temp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.black_circle_bullet, 0, 0, 0);
            temp.setCompoundDrawablePadding(5);
            
            rootLinearLayout.addView(temp);
        }

    }
}