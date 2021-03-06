package com.evan.workoutapp.ui.workouts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorkoutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WorkoutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is workout fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setText(String value) {
        mText.setValue(value);
    }
}