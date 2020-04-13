package com.CS5520.sleepforest.ui.Shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public ShopViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to Tree Shop");
    }

    public LiveData<String> getText() {
        return mText;
    }
}