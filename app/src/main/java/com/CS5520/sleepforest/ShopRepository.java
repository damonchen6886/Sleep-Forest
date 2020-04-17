package com.CS5520.sleepforest;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.List;

public class ShopRepository {
    private ShopDao shopDao;
    private MutableLiveData<Integer> results = new MutableLiveData<>();
    private int currentCoins;

    public ShopRepository(Application application){
        ShopRoomDatabase db;
        db= ShopRoomDatabase.getDatabase(application);
        shopDao = db.shopDao();

        currentCoins = shopDao.findCurrentCoins("totalCoins");
    }

    private void asyncFinished(Integer r) {
        results.setValue(r);
    }

    private static class QueryAsyncTask extends AsyncTask<String, Void,  Integer> {
        private ShopDao asyncTaskDao;
        private ShopRepository delegate = null;
        QueryAsyncTask(ShopDao dao){
            asyncTaskDao = dao;
        }
        @Override
        protected Integer doInBackground(final String... parms) {
            return asyncTaskDao.findCurrentCoins(parms[0]);
        }

        @Override
        protected void onPostExecute(Integer result){
            delegate.asyncFinished(result);
        }
    }



    private static class InsertAsyncTask extends AsyncTask<Shop, Void, Void> {

        private ShopDao asyncTaskDao;

        InsertAsyncTask(ShopDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Shop... shops) {
            asyncTaskDao.insertShop(shops[0]);
            return null;
        }
    }




    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private ShopDao asyncTaskDao;

        DeleteAsyncTask(ShopDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteShop();
            return null;
        }
    }


    private static class UpdateAsyncTask extends AsyncTask<Integer, Void, Void> {

        private ShopDao asyncTaskDao;

        UpdateAsyncTask(ShopDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            asyncTaskDao.updateCoins(integers[0]);
            return null;
        }
    }



    public void deleteShop(){
        DeleteAsyncTask task = new DeleteAsyncTask(shopDao);
        task.execute();

    }

    public void insertShop(Shop shop){
        InsertAsyncTask task = new InsertAsyncTask(shopDao);
        task.execute(shop);
    }


    public void updateCoins(int number){
        UpdateAsyncTask task = new UpdateAsyncTask(shopDao);
        task.execute(number);
    }

    public void findCurrentCoins(String column){
        QueryAsyncTask task = new QueryAsyncTask(shopDao);
        task.delegate = this;
        task.execute(column);
    }

    public int getCoins(){
        return currentCoins;
    }

    public MutableLiveData<Integer> getResults(){
        return results;
    }


//    public void addCoins(Shop shop){
//        InsertAsyncTask task = new InsertAsyncTask(shopDao);
//        task.execute(shop);
//    }





}
