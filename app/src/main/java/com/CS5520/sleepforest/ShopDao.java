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


    @Query("DELETE FROM shop")
    void deleteShop();

    @Query("SELECT * FROM shop")
    LiveData<List<Shop>> getAllShops();

}

