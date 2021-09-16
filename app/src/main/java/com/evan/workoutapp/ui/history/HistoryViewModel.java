package com.evan.workoutapp.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the History Fragment");
    }

    public LiveData<String> getText() { return mText; }

    public void setText(String value) {
        mText.setValue(value);
    }
}
