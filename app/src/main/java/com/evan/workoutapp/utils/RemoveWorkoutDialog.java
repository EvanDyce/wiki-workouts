package com.evan.workoutapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.FirestoreFunctions;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.ui.custom.CustomFragment;
import com.evan.workoutapp.ui.custom.MakeCustomWorkoutActivity;
import com.evan.workoutapp.user.CurrentUserSingleton;

public class RemoveWorkoutDialog extends Dialog {
    private Button cancelButton, removeButton;
    private int index;
    private Workout workout;
    private Fragment fragment;

    public RemoveWorkoutDialog(Context context, Workout workout, int index, Fragment fragment) {
        super(context);
        this.workout = workout;
        this.index = index;
        this.fragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.remove_workout_dialog);

        View workoutCard = findViewById(R.id.workoutCardIncluded);
        TextView title = workoutCard.findViewById(R.id.workoutTitleCategory);
        title.setText(this.workout.getName());

        ImageView imageView = workoutCard.findViewById(R.id.workoutImageCategory);
        imageView.setImageResource(this.workout.getImageID());

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
                CurrentUserSingleton.getInstance().getUserWorkouts().remove(index);
                FirestoreFunctions.updateUserData();
                dismiss();

                if (fragment instanceof CustomFragment) {
                    ((CustomFragment) fragment).setAdapter();
                }
            }
        });
    }

}
