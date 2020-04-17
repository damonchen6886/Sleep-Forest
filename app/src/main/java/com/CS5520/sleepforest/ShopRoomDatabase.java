package com.CS5520.sleepforest;


import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Shop.class}, version =1,exportSchema = false)
public abstract class ShopRoomDatabase extends RoomDatabase {

    public abstract ShopDao shopDao();
    private static ShopRoomDatabase INSTANCE;

    static ShopRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ShopRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShopRoomDatabase.class,"shop_database").build();
                }
            }
        }
            return INSTANCE;
        }
}
