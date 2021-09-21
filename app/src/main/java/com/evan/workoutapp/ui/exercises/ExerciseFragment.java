package com.evan.workoutapp.ui.exercises;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.evan.workoutapp.data.Exercises;
import com.evan.workoutapp.databinding.FragmentExercisesBinding;
import com.evan.workoutapp.volley.VolleyUtils;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExerciseFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;
    private FragmentExercisesBinding binding;

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

        Button makeRequest = binding.buttonMakeRequest;
        ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<>();
        makeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolleyUtils.makeJsonObjectRequest(getContext(), "https://wger.de/api/v2/exerciseinfo/?language=2", new VolleyUtils.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.e("REQUEST", message);
                    }

                    /**
                     * Adds successfully queried JSONObjects to list and recursively calls next url
                     * Once all objects are added to the list calls Exercises.getDataFromObjects to unpack data
                     * @param response the response from successful API query
                     */
                    @Override
                    public void onResponse(Object response) {
                        JSONObject obj = (JSONObject) response;

                        jsonObjectArrayList.add(obj);

                        try {
                            String url = obj.getString("next");

                            if (url.length() > 0) {
                                VolleyUtils.makeJsonObjectRequest(getContext(), url, this);
                            }
                        } catch (Exception e) {
                            Log.e("REQUEST", e.getLocalizedMessage());
                        }

                        if (jsonObjectArrayList.size() == 12) {
                            Exercises.getDataFromObjects(jsonObjectArrayList);
                        }
                    }
                });
            }
        });

        Toast.makeText(getContext(), "" + jsonObjectArrayList.size(), Toast.LENGTH_SHORT).show();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}