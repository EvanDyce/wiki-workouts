package com.evan.workoutapp.ui.history;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.data.workout.FinishedWorkout;
import com.evan.workoutapp.data.workout.Workout;
import com.evan.workoutapp.databinding.FragmentHistoryBinding;
import com.evan.workoutapp.ui.GeneralWorkoutAdapter;
import com.evan.workoutapp.ui.HistoricWorkoutAdapter;
import com.evan.workoutapp.ui.history.HistoryViewModel;
import com.evan.workoutapp.user.CurrentUserSingleton;
import com.evan.workoutapp.utils.RemoveWorkoutDialog;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private FragmentHistoryBinding binding;
    private ArrayList<FinishedWorkout> finishedWorkouts;
    private GeneralWorkoutAdapter workoutAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setAdapter();

        return root;
    }

    public void setAdapter() {
        finishedWorkouts = CurrentUserSingleton.getInstance().getFinishedWorkouts();
        final RecyclerView workoutRV = binding.workoutRecyclerview;
        workoutAdapter = new HistoricWorkoutAdapter(getContext(), finishedWorkouts, listener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        workoutRV.setLayoutManager(linearLayoutManager);
        workoutRV.setAdapter(workoutAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    GeneralWorkoutAdapter.WorkoutClickedListener listener = new GeneralWorkoutAdapter.WorkoutClickedListener() {
        @Override
        public void onWorkoutClicked(int position) {

        }

        @Override
        public void onWorkoutLongClicked(int position) {
            RemoveWorkoutDialog rwd = new RemoveWorkoutDialog(getContext(), CurrentUserSingleton.getInstance().getFinishedWorkouts(),
                    position, HistoryFragment.this);
            rwd.show();
        }
    };
}
