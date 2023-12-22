package ru.pin120.via.accountingsoftwaremobile.ui.software;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SoftwareViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> selectedCategory;

    public SoftwareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(null);
        selectedCategory = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<String> getSelectedCategory() {
        return selectedCategory;
    }
}
