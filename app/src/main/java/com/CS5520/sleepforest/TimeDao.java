package com.CS5520.sleepforest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
interface TimeDao {
    @Insert
    void insertTime(Time time);

    @Query("SELECT * FROM time where timeId = :id")
    List<Time> findTime(int id);


    @Query("DELETE FROM time")
    void deleteTime();

    @Query("SELECT * FROM time")
    LiveData<List<Time>> getAllTimes();

}
