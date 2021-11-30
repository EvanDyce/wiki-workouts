package com.evan.workoutapp.ui.exercises;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.databinding.FragmentExercisesBinding;
import com.evan.workoutapp.ui.ExerciseFilteringInterface;

import java.util.ArrayList;
import java.util.List;

public class ExerciseFragment extends Fragment implements ExerciseFilteringInterface {

    // boolean flag for spinner
    private static boolean firstTimeLoaded = true;

    // string to track category selected
    private String currentCategoryFilter = "All";

    private ExerciseViewModel exerciseViewModel;
    private FragmentExercisesBinding binding;
    private final String TAG = "EXERCISE_FRAGMENT";
    private ArrayList<Exercises.Exercise> exerciseArrayList;
    private ExerciseAdapter exerciseAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);

        binding = FragmentExercisesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView exerciseRV = binding.exerciseRecyclerview;

        // initialize adapter and pass arraylist with the data
        exerciseArrayList = Exercises.getAllExercises();
        exerciseAdapter = new ExerciseAdapter(getContext(), exerciseArrayList);

        // setting layout manager for the recycler view
        // making a vertical list so passing that value
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        exerciseRV.setLayoutManager(linearLayoutManager);
        exerciseRV.setAdapter(exerciseAdapter);

        // getting the filter edit text and adding onTextChangedListener
        final EditText filterText = binding.editTextFilter;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(), R.layout.spinner_item, spinnerList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // getting the spinner and adding the adapter
        Spinner spinner = binding.exerciseCategorySpinner;
        spinner.setAdapter(adapter);

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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}