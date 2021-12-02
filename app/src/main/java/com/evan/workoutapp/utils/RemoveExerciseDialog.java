package com.evan.workoutapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.ui.custom.MakeCustomWorkoutActivity;

public class RemoveExerciseDialog extends Dialog {

    private Context context;
    private Button cancelButton, removeButton;
    private int index;
    private Exercises.Exercise exercise;

    public RemoveExerciseDialog(@NonNull Context context, Exercises.Exercise exercise, int index) {
        super(context);
        this.index = index;
        this.exercise = exercise;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.remove_exercise_dialog);

        // populating the things in the dialog starting with exercise card
        View exerciseCard = findViewById(R.id.exerciseCardIncluded);
        TextView title = exerciseCard.findViewById(R.id.exerciseTitle);
        title.setText(this.exercise.getName());

        TextView description = exerciseCard.findViewById(R.id.exerciseDescription);
        description.setText(this.exercise.getDescription());

        TextView equipment = exerciseCard.findViewById(R.id.exerciseEquipment);
        equipment.setText(this.exercise.getEquipment());

        ImageView image = exerciseCard.findViewById(R.id.exerciseCardImage);
        image.setImageResource(this.exercise.getImageID());

        // buttons and setting their listeners
        cancelButton = findViewById(R.id.dialogCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        removeButton = findViewById(R.id.dialogRemoveButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeCustomWorkoutActivity.removeExercise(index);
                dismiss();
            }
        });

    }
}
