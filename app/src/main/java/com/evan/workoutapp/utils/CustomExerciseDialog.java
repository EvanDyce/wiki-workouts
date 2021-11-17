package com.evan.workoutapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises;

public class CustomExerciseDialog extends Dialog implements View.OnClickListener{

    public Activity activity;
    public Dialog dialog;
    public Button okButton;
    public Exercises.Exercise exercise;

    public CustomExerciseDialog(Activity a, Exercises.Exercise exercise) {
        super(a);
        this.activity = a;
        this.exercise = exercise;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exercise_dialog);

        // get the exercise card and set it with all the information from instance exercise passed in constructor
        View exerciseCard = findViewById(R.id.exerciseCardIncluded);
        TextView title = exerciseCard.findViewById(R.id.exerciseTitle);
        title.setText(this.exercise.getName());

        TextView description = exerciseCard.findViewById(R.id.exerciseDescription);
        description.setText(this.exercise.getDescription());

        TextView equipment = exerciseCard.findViewById(R.id.exerciseEquipment);
        equipment.setText(this.exercise.getEquipment());

        ImageView image = exerciseCard.findViewById(R.id.exerciseCardImage);
        image.setImageResource(this.exercise.getImageID());

        okButton = findViewById(R.id.dialogOkButton);
        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
