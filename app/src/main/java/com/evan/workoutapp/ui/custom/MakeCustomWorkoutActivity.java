package com.evan.workoutapp.ui.custom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.evan.workoutapp.MainActivity;
import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.data.FirestoreFunctions;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.databinding.ActivityMakeCustomWorkoutBinding;
import com.evan.workoutapp.ui.custom.creation.ExerciseSelectionActivity;
import com.evan.workoutapp.ui.custom.creation.ExerciseSelectionAdapter;
import com.evan.workoutapp.ui.workouts.WorkoutInformationActivity;
import com.evan.workoutapp.user.CurrentUserSingleton;
import com.evan.workoutapp.utils.CustomExerciseDialog;
import com.evan.workoutapp.utils.RemoveExerciseDialog;
import com.evan.workoutapp.volley.VolleySingleton;

import java.io.Serializable;
import java.util.ArrayList;

public class MakeCustomWorkoutActivity extends AppCompatActivity {

    private ActivityMakeCustomWorkoutBinding binding;
    private static ObservableArrayList<Exercises.Exercise> exercisesInWorkout = new ObservableArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMakeCustomWorkoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();

        // get the action bar and set teh color and home button
        if (actionBar == null) Log.e("FUCKER", "IT IS NULL");
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D26466")));
        actionBar.setDisplayHomeAsUpEnabled(true);

        populateExercises();

        // setting the adapters for the spinners
        String[] workoutCategories = {"Chest", "Shoulders", "Arms", "Abs", "Legs", "Back", "Calves"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(MakeCustomWorkoutActivity.this, R.layout.spinner_item, workoutCategories);
        binding.spinnerCustomWorkoutCategory.setAdapter(adapter);

        String[] workoutDifficulties = {"Beginner", "Intermediate", "Expert", "It's About Drive"};
        ArrayAdapter<CharSequence> dapter = new ArrayAdapter<>(MakeCustomWorkoutActivity.this, R.layout.spinner_item, workoutDifficulties);
        binding.spinnerCustomWorkoutDifficulty.setAdapter(dapter);

        String[] workoutLength = {"Less than 30 minutes", "30-60 minutes", "60-90 minutes", "More than 90 minutes"};
        ArrayAdapter<CharSequence> apter = new ArrayAdapter<>(MakeCustomWorkoutActivity.this, R.layout.spinner_item, workoutLength);
        binding.spinnerCustomWorkoutLength.setAdapter(apter);

        // setting add Exercise onClick
        binding.buttonAddExerciseToCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSharedPreferences();
                Intent intent = new Intent(MakeCustomWorkoutActivity.this, ExerciseSelectionActivity.class);
                startActivity(intent);
            }
        });

        binding.startWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createWorkout()) {
                    goBackToMainActivity();
                }
            }
        });

        exercisesInWorkout.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Exercises.Exercise>>() {
            @Override
            public void onChanged(ObservableList<Exercises.Exercise> sender) {
                populateExercises();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Exercises.Exercise> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<Exercises.Exercise> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeMoved(ObservableList<Exercises.Exercise> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Exercises.Exercise> sender, int positionStart, int itemCount) {
                populateExercises();
            }
        });

        setViewsWithPreferences();
    }

    /**
     * on click for the back button on action bar
     *
     * @param item menuitem that was clicked, always back button
     * @return returns true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            goBackToMainActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSharedPreferences() {
        // context method that returns some class with SharedPreferences interface implemented
        SharedPreferences sp = getSharedPreferences("workout", 0);
        // returns nested Editor interface to make the changes
        SharedPreferences.Editor editor = sp.edit();

        String name = String.valueOf(binding.etCustomWorkoutName.getText());
        int category_index = binding.spinnerCustomWorkoutCategory.getSelectedItemPosition();
        int difficulty_index = binding.spinnerCustomWorkoutDifficulty.getSelectedItemPosition();
        int length_index = binding.spinnerCustomWorkoutLength.getSelectedItemPosition();

        editor.putString("name", name);
        editor.putInt("category", category_index);
        editor.putInt("difficulty", difficulty_index);
        editor.putInt("length", length_index);
        editor.apply();
    }

    private void setViewsWithPreferences() {
        SharedPreferences sp = this.getSharedPreferences("workout", 0);
        String name = sp.getString("name", "");
        int category_index = sp.getInt("category", 0);
        int difficulty_index = sp.getInt("difficulty", 0);
        int length_index = sp.getInt("length", 0);

        binding.etCustomWorkoutName.setText(name);
        binding.spinnerCustomWorkoutCategory.setSelection(category_index);
        binding.spinnerCustomWorkoutDifficulty.setSelection(difficulty_index);
        binding.spinnerCustomWorkoutLength.setSelection(length_index);
    }

    public static void addExerciseToWorkout(Exercises.Exercise e) {
        exercisesInWorkout.add(e);
    }

    public static void removeExercise(int index) {
        exercisesInWorkout.remove(index);
    }

    public void populateExercises() {
        binding.llAddedExercises.removeAllViews();
        for (Exercises.Exercise exercise : exercisesInWorkout) {
            Log.e("HERROR", exercise.toString());
            TextView temp = new TextView(this);
            temp.setText(exercise.getName());
            temp.setId(Integer.valueOf(exercise.getId()));
            temp.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            temp.setTextColor(getResources().getColor(R.color.black));
            temp.setTextSize(18.0F);
            temp.setPadding(0, 5, 0, 0);
            temp.setPaintFlags(temp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            temp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.black_circle_bullet, 0, 0, 0);
            temp.setCompoundDrawablePadding(25);
            temp.setLongClickable(true);
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomExerciseDialog ced = new CustomExerciseDialog(MakeCustomWorkoutActivity.this, exercise);
                    ced.show();
                }
            });

            temp.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // get index of view in linearlayout. Same as the index in the exercise list
                    int index = ((ViewGroup) v.getParent()).indexOfChild(v);
                    RemoveExerciseDialog red = new RemoveExerciseDialog(v.getContext(), exercisesInWorkout.get(index), index);
                    red.show();
                    return true;
                }
            });
            binding.llAddedExercises.addView(temp);
        }
    }

    /**
     * creates the workout and adds to current users list
     * @return true if all inputs are valid, false if not
     */
    private boolean createWorkout() {
        // information required
        // name, category, exercise list, primary, secondary,
        // description, difficulty ,length

        String name = String.valueOf(binding.etCustomWorkoutName.getText());
        String category = ((String) binding.spinnerCustomWorkoutCategory.getSelectedItem());
        String difficulty = ((String) binding.spinnerCustomWorkoutDifficulty.getSelectedItem());
        String length = ((String) binding.spinnerCustomWorkoutLength.getSelectedItem());

        if (name.length() == 0) {
            binding.etCustomWorkoutName.setError("Please enter a name");
            binding.etCustomWorkoutName.requestFocus();
            return false;
        }


        Workout workout = new Workout(name, category, difficulty, length, exercisesInWorkout);
        CurrentUserSingleton.getInstance().addUserCustomWorkout(workout);

        SharedPreferences sharedPreferences = getSharedPreferences("workout", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", "");
        editor.putInt("category", 0);
        editor.putInt("difficulty", 0);
        editor.putInt("length", 0);
        editor.apply();

        FirestoreFunctions.updateUserData();
        return true;
    }

    private void goBackToMainActivity() {
        Intent intent = new Intent(MakeCustomWorkoutActivity.this, MainActivity.class);
        intent.putExtra("fragment", MainActivity.CUSTOM_WORKOUT_FRAGMENT);
        startActivity(intent);
    }
}