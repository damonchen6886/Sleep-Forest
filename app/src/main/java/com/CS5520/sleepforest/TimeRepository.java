package com.CS5520.sleepforest;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class TimeRepository {
    private TimeDao timeDao;
    private MutableLiveData<List<Time>> results = new MutableLiveData<>();
    private LiveData<List<Time>> times;

    public TimeRepository(Application application){
        TimeRoomDatabase db;
        db= TimeRoomDatabase.getDatabase(application);
        timeDao = db.timeDao();

        times= timeDao.getAllTimes();
    }

    private void asyncFinished(List<Time> r){results.setValue(r);}

    private static class QueryAsyncTask extends AsyncTask<Integer, Void,  List<Time>>{
        private TimeDao asyncTaskDao;
        private TimeRepository delegate = null;
        QueryAsyncTask(TimeDao dao){
            asyncTaskDao = dao;
        }
        @Override
        protected List<Time> doInBackground(Integer... integers) {
            return asyncTaskDao.findTime(integers[0]);
        }

        @Override
        protected void onPostExecute(List<Time> times){
            delegate.asyncFinished(times);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Time, Void, Void> {

        private TimeDao asyncTaskDao;

        InsertAsyncTask(TimeDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Time... times) {
            asyncTaskDao.insertTime(times[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private TimeDao asyncTaskDao;

        DeleteAsyncTask(TimeDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteTime();
            return null;
        }
    }

    public void insertTime(Time time){
        InsertAsyncTask task = new InsertAsyncTask(timeDao);
        task.execute(time);

    }

    public void deleteTime(){
        DeleteAsyncTask task = new DeleteAsyncTask(timeDao);
        task.execute();

    }

    public LiveData<List<Time>> getTimes() {
        return times;
    }

    public MutableLiveData<List<Time>> getResults() {
        return results;
    }

    public void findTime(int id){
        QueryAsyncTask task = new QueryAsyncTask(timeDao);
        task.delegate = this;
        task.execute(id);
    }
}
