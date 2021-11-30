package com.evan.workoutapp.ui.custom.creation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.databinding.ActivityExerciseSelectionBinding;
import com.evan.workoutapp.databinding.ActivityMainBinding;
import com.evan.workoutapp.ui.ExerciseFilteringInterface;
import com.evan.workoutapp.ui.custom.MakeCustomWorkoutActivity;
import com.evan.workoutapp.ui.exercises.ExerciseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExerciseSelectionActivity extends AppCompatActivity implements ExerciseFilteringInterface, ExerciseSelectionAdapter.WorkoutClickedListener {

    private static boolean firstTimeLoaded = true;
    private String currentCategoryFilter = "All";

    private ActivityExerciseSelectionBinding binding;
    private final String TAG = "Exercise_Selection";
    private ArrayList<Exercises.Exercise> exerciseArrayList;
    private ExerciseSelectionAdapter exerciseAdapter;
    private ArrayList<Exercises.Exercise> exerciseInWorkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExerciseSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();

        // get the action bar and set teh color and home button
        if (actionBar == null) Log.e("FUCKER", "IT IS NULL");
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D26466")));
        actionBar.setDisplayHomeAsUpEnabled(true);

        // access included fragment with binding.id.view notation
        final RecyclerView exerciseRV = binding.includedExerciseFragment.exerciseRecyclerview;

        exerciseArrayList = Exercises.getAllExercises();
        exerciseAdapter = new ExerciseSelectionAdapter(this, exerciseArrayList, this::onWorkoutClicked);

        // setting layout manager for the recycler view
        // making a vertical list so passing that value
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        exerciseRV.setLayoutManager(linearLayoutManager);
        exerciseRV.setAdapter(exerciseAdapter);

        // getting the filter edit text and adding onTextChangedListener
        final EditText filterText = binding.includedExerciseFragment.editTextFilter;

        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // default filter call from interface
                filter(editable.toString(), currentCategoryFilter, exerciseArrayList, exerciseAdapter);
            }
        });


        // making list of strings that will be displayed in spinner, in this case teh categories of exercises to filter by
        List<String> spinnerList = new ArrayList<>();
        spinnerList.add("All");
        spinnerList.add("Chest");
        spinnerList.add("Shoulders");
        spinnerList.add("Legs");
        spinnerList.add("Calves");
        spinnerList.add("Back");
        spinnerList.add("Arms");
        spinnerList.add("Abs");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, spinnerList);

        // getting the spinner and adding the stringArrayAdapter
        Spinner spinner = binding.includedExerciseFragment.exerciseCategorySpinner;
        spinner.setAdapter(stringArrayAdapter);

        // setting teh onItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (firstTimeLoaded) {
                    firstTimeLoaded = false;
                    return;
                }

                String name = adapterView.getAdapter().getItem(i).toString();
                currentCategoryFilter = name;
                filterByCategory(name, exerciseArrayList, exerciseAdapter);
                Log.d(TAG, adapterView.getAdapter().getItem(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onWorkoutClicked(int position) {
        MakeCustomWorkoutActivity.addExerciseToWorkout(exerciseArrayList.get(position));
        startActivity(new Intent(this, MakeCustomWorkoutActivity.class));
    }

    /**
     * on click for the back button on action bar
     * @param item menuitem that was clicked, always back button
     * @return returns true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MakeCustomWorkoutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}