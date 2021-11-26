package com.evan.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.evan.workoutapp.databinding.ActivityMakeCustomWorkoutBinding;

public class MakeCustomWorkoutActivity extends AppCompatActivity {

    private ActivityMakeCustomWorkoutBinding binding;

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
    }

    /**
     * on click for the back button on action bar
     * @param item menuitem that was clicked, always back button
     * @return returns true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MakeCustomWorkoutActivity.this, MainActivity.class);
            intent.putExtra("fragment", MainActivity.CUSTOM_WORKOUT_FRAGMENT);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}