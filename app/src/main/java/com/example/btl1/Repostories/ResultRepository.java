package com.example.btl1.Repostories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.btl1.database.AppDatabase;
import com.example.btl1.database.dao.ResultDao;
import com.example.btl1.database.entity.ResultEntity;
import com.example.btl1.models.Result;

import java.util.List;

public class ResultRepository {
    private ResultDao resultDao;
    private LiveData<List<ResultEntity>> allResults;

    public ResultRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        resultDao = db.resultDao();
    }

    public void insert(ResultEntity result) {
        new InsertResultAsyncTask(resultDao).execute(result);
    }

    public List<ResultEntity> getAllResults() {
        return resultDao.getAllResults(); // Fetch as List
    }

    public ResultEntity getResultsByTime(int time) {
        return resultDao.getResultsByTime(time);
    }


    private static class InsertResultAsyncTask extends AsyncTask<ResultEntity, Void, Void> {
        private ResultDao resultDao;

        private InsertResultAsyncTask(ResultDao resultDao) {
            this.resultDao = resultDao;
        }

        @Override
        protected Void doInBackground(ResultEntity... resultEntities) {
            resultDao.insert(resultEntities[0]);
            return null;
        }
    }
}