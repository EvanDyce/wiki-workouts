package com.evan.workoutapp.ui.exercises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.R;
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

        final RecyclerView exerciseRV = binding.exerciseRecyclerview;
        // initialize adapter and pass arraylist with the data
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(getContext(), Exercises.getAllExercises());

        // setting layout manager for the recycler view
        // making a vertical list so passing that value
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        exerciseRV.setLayoutManager(linearLayoutManager);
        exerciseRV.setAdapter(exerciseAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // inflate the menu like usla and looke for teh menu item that contains the searchview
    // implement hte textlistener that we will use to listen for changes
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

}