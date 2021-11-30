package com.evan.workoutapp.ui.custom;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.MainActivity;
import com.evan.workoutapp.ui.workouts.WorkoutInformationActivity;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.databinding.FragmentCustomWorkoutsBinding;
import com.evan.workoutapp.ui.GeneralWorkoutAdapter;
import com.evan.workoutapp.user.CurrentUserSingleton;

import java.util.ArrayList;

public class CustomFragment extends Fragment implements GeneralWorkoutAdapter.WorkoutClickedListener {

    private CustomViewModel customViewModel;
    private FragmentCustomWorkoutsBinding binding;
    private ArrayList<Workout> customs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customViewModel =
                new ViewModelProvider(this).get(CustomViewModel.class);

        binding = FragmentCustomWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // get and display the custom workouts of the current user
        customs = CurrentUserSingleton.getInstance().getUserWorkouts();
        customs.add(new Workout("Test", "THIS IS DESC", "Chest", new ArrayList<>()));
        final RecyclerView workoutRV = binding.workoutRecyclerview;
        GeneralWorkoutAdapter workoutAdapter = new GeneralWorkoutAdapter(getContext(), CurrentUserSingleton.getInstance().getUserWorkouts(), this::onWorkoutClicked);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        workoutRV.setLayoutManager(linearLayoutManager);
        workoutRV.setAdapter(workoutAdapter);

        // set onclick for teh button and  make it start the make workout intent
        binding.addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), MakeCustomWorkoutActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onWorkoutClicked(int position) {
        Intent intent = new Intent(this.getContext(), WorkoutInformationActivity.class);
        intent.putExtra("workout_index", position);
        intent.putExtra("workout_list", customs);
        Intent next_intent = new Intent(this.getContext(), MainActivity.class);
        next_intent.putExtra("fragment", MainActivity.CUSTOM_WORKOUT_FRAGMENT);
        intent.putExtra("next_intent", next_intent);
        startActivity(intent);
    }
}