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

    @Query("SELECT * FROM time where timeId = :totalCoins")
    List<Shop> findCurrentCoins(int totalCoins);

    @Update
    void subtractCoins();

    @Update
    void addCoins();

}
