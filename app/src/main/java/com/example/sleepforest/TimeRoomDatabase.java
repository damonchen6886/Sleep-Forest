package com.example.sleepforest;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.time.zone.ZoneOffsetTransitionRule;

@Database(entities = {Time.class}, version = 1)
public abstract class TimeRoomDatabase extends RoomDatabase {

    public abstract TimeDao timeDao();
    private static  TimeRoomDatabase INSTANCE;


    static TimeRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (TimeRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TimeRoomDatabase.class, "time_database").build();
                }
            }
        }

        return INSTANCE;
    }

}
