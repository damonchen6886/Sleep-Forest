package com.CS5520.sleepforest;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShopDao {
    @Insert
    void insertShop(Shop shop);

//    @Query("SELECT :totalCoins FROM shop where shopId = 0")
//    List<Shop> findCurrentCoins(int totalCoins);
//
//    @Query("UPDATE SHOP SET totalCoins  = (select totalCoins from  shop where shopId = 0) - :numbers WHERE shopId = 0")
//    void subtractCoins(int numbers);
//
//    @Query("UPDATE SHOP SET totalCoins  = :coins WHERE shopId = 0")
//    int addCoins(int coins);

}
