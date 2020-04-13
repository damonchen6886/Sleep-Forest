package com.CS5520.sleepforest;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time")
public class Time {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name="timeId")
    private int id;

    @ColumnInfo(name="hour")
    private int hour;

    @ColumnInfo(name="minute")
    private int minute;

    public Time(int id, int hour, int minute){
        this.id = id;
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinute() {
        return minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String toString(){
        return this.hour + " : " + this.minute;
    }
}
