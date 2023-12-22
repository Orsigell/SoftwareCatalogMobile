package ru.pin120.via.accountingsoftwaremobile.ui.screens;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScreensViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ScreensViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(null);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
