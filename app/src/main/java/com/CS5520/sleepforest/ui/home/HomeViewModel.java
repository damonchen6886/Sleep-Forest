package com.CS5520.sleepforest.ui.home;

import android.app.Application;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.CS5520.sleepforest.Shop;
import com.CS5520.sleepforest.ShopRepository;

public class HomeViewModel extends AndroidViewModel {

    private ShopRepository shopRepository;
    private int coins;
    private MutableLiveData<Integer> results;

    private MutableLiveData<String> mText;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        shopRepository = new ShopRepository(application);
        coins = shopRepository.getCoins();
        results = shopRepository.getResults();
    }

    public MutableLiveData<Integer> getResults(){
        return results;
    }
    public int getCoins(){
        return coins;
    }
    public void insertCoin(Shop shop){
        shopRepository.insertShop(shop);

    }
    public void updateCoin(int num){
        shopRepository.updateCoins(num);
    }

    public void deleteCoin(){
        shopRepository.deleteShop();
    }

    public void  findCurrentCoin(String column){
        shopRepository.findCurrentCoins(column);
    }






    public LiveData<String> getText() {
        return mText;
    }
}