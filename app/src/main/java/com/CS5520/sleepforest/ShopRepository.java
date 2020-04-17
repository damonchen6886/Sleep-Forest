package com.CS5520.sleepforest;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.List;

public class ShopRepository {
    private ShopDao shopDao;
    private MutableLiveData<List<Shop>> results = new MutableLiveData<>();
    private LiveData<List<Shop>> shops;

    public ShopRepository(Application application){
        ShopRoomDatabase db;
        db= ShopRoomDatabase.getDatabase(application);
        shopDao = db.shopDao();

       shops= shopDao.getAllShops();
    }

    private void asyncFinished(List<Shop> r){results.setValue(r);}

    private static class QueryAsyncTask extends AsyncTask<Integer, Void,  List<Shop>>{
        private ShopDao asyncTaskDao;
        private ShopRepository delegate = null;
        QueryAsyncTask(ShopDao dao){
            asyncTaskDao = dao;
        }
        @Override
        protected List<Shop> doInBackground(Integer... integers) {
            return asyncTaskDao.findShop(integers[0]);
        }

        @Override
        protected void onPostExecute(List<Shop> shops){
            delegate.asyncFinished(shops);
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

    public void insertShop(Shop shop){
        ShopRepository.InsertAsyncTask task = new ShopRepository.InsertAsyncTask(shopDao);
        task.execute(shop);

    }

    public void deleteShop(){
        ShopRepository.DeleteAsyncTask task = new ShopRepository.DeleteAsyncTask(shopDao);
        task.execute();

    }

    public LiveData<List<Shop>> getShops() {
        return shops;
    }

    public MutableLiveData<List<Shop>> getResults() {
        return results;
    }

    public void findShop(int id){
        ShopRepository.QueryAsyncTask task = new ShopRepository.QueryAsyncTask(shopDao);
        task.delegate = this;
        task.execute(id);
    }


}
