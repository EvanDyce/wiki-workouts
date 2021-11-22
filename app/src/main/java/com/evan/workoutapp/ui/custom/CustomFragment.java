package com.evan.workoutapp.ui.custom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.databinding.FragmentCustomWorkoutsBinding;
import com.evan.workoutapp.user.CurrentUserSingleton;

import java.util.ArrayList;

public class CustomFragment extends Fragment {

    private CustomViewModel customViewModel;
    private FragmentCustomWorkoutsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customViewModel =
                new ViewModelProvider(this).get(CustomViewModel.class);

        binding = FragmentCustomWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Workout> custom = CurrentUserSingleton.getInstance().getUserWorkouts();
        Log.e("FUCKERY", custom.toString());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}