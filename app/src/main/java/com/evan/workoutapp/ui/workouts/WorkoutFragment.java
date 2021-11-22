package com.evan.workoutapp.ui.workouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.WorkoutInformationActivity;
import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.data.workout.PremadeWorkouts;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.databinding.FragmentWorkoutsBinding;
import com.evan.workoutapp.ui.GeneralWorkoutAdapter;

import java.util.ArrayList;

public class WorkoutFragment extends Fragment implements GeneralWorkoutAdapter.WorkoutClickedListener {

    private WorkoutViewModel workoutViewModel;
    private FragmentWorkoutsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        workoutViewModel =
                new ViewModelProvider(this).get(WorkoutViewModel.class);

        binding = FragmentWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView workoutRV = binding.workoutRecyclerview;

        // init adapter and pass arraylist with the data
        GeneralWorkoutAdapter workoutAdapter = new GeneralWorkoutAdapter(getContext(), PremadeWorkouts.getPremadeWorkoutsArraylist(), this::onWorkoutClicked);

        // setting layout manager for recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        workoutRV.setLayoutManager(linearLayoutManager);
        workoutRV.setAdapter(workoutAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * onClick for when a workout is clicked in the workout frag recycler view
     * @param position position of workout in the recycler view so I know which one it
     *                 is to start teh intent
     */
    @Override
    public void onWorkoutClicked(int position) {
        Intent intent = new Intent(this.getContext(), WorkoutInformationActivity.class);
        intent.putExtra("workout_index", position);
        startActivity(intent );
    }
}