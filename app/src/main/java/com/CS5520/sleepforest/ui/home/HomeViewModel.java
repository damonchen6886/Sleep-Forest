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
import com.CS5520.sleepforest.Time;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private ShopRepository shopRepository;
    private LiveData<List<Shop>>  allshops;
    private MutableLiveData<List<Shop>> results;

    private MutableLiveData<String> mText;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        shopRepository = new ShopRepository(application);
        allshops= shopRepository.getShops();
        results = shopRepository.getResults();
    }

    public MutableLiveData<List<Shop>> getResults(){
        return results;
    }
    public LiveData<List<Shop>> getCoins(){

        return allshops;
    }
    public void insertCoin(Shop shop){
        shopRepository.insertShop(shop);

    }
//    public void updateCoin(int num){
//        shopRepository.updateCoins(num);
//    }

    public void deleteCoin(){
        shopRepository.deleteShop();
    }

    public void  findCoin(int id){
        shopRepository.findShop(id);
    }






    public LiveData<String> getText() {
        return mText;
    }
}