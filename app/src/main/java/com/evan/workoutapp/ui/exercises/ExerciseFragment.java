package com.evan.workoutapp.ui.exercises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.data.FirestoreFunctions;
import com.evan.workoutapp.databinding.FragmentExercisesBinding;
import com.evan.workoutapp.volley.VolleyUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class ExerciseFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;
    private FragmentExercisesBinding binding;
    private final String TAG = "EXERCISE_FRAGMENT";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);

        binding = FragmentExercisesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textExercises;

        exerciseViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}