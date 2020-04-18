package com.CS5520.sleepforest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface ShopDao {
    @Insert
    void insertShop(Shop shop);

    @Query("SELECT * FROM shop where shopId = :id")
    List<Shop> findShop(int id);

    @Query("UPDATE SHOP SET totalCoins  = " +
            "CASE  WHEN (select totalCoins from  shop where shopId = 1) + :numbers  < 0 THEN (select totalCoins from  shop where shopId = 1)" +
            "ELSE (select totalCoins from  shop where shopId = 1) + :numbers END " +
            "WHERE shopId = 1")
    void updateCoins(int numbers);


    @Query("DELETE FROM shop")
    void deleteShop();

    @Query("SELECT * FROM shop")
    LiveData<List<Shop>> getAllShops();

}

