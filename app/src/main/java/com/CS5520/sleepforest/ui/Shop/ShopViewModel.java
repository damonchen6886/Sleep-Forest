package com.CS5520.sleepforest.ui.Shop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.CS5520.sleepforest.Shop;
import com.CS5520.sleepforest.ShopRepository;

import java.util.List;

public class ShopViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

    private ShopRepository shopRepository;
    private LiveData<List<Shop>>  allshops;
    private MutableLiveData<List<Shop>> results;


    public ShopViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to Tree Shop");
        shopRepository = new ShopRepository(application);
//        deleteCoin();
//        insertCoin(new Shop(1, 0));
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

    public void updateCoin(int c){
        shopRepository.updateShop(c);
    }




    public LiveData<String> getText() {
        return mText;
    }
}