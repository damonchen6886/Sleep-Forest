package com.CS5520.sleepforest.ui.tools;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.CS5520.sleepforest.Time;
import com.CS5520.sleepforest.TimeRepository;

import java.util.List;

public class ToolsViewModel extends AndroidViewModel {
    private TimeRepository repository;
    private LiveData<List<Time>>  alltimes;
    private MutableLiveData<List<Time>> results;

    private MutableLiveData<String> mText;


    public ToolsViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("Time to go to bed：");
        repository = new TimeRepository(application);
        alltimes = repository.getTimes();
        results = repository.getResults();
    }

   public  MutableLiveData<List<Time>> getResults(){
        return results;
    }
    public LiveData<List<Time>> getAlltimes(){
        return alltimes;
    }

    public void insertTime(Time t){
        repository.insertTime(t);
    }

    public void findTime(int id){
        repository.findTime(id);
    }

    public void deleteTime(){
        repository.deleteTime();
    }

    public LiveData<String> getText() {
        return mText;
    }
}