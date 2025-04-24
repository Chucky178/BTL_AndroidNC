package com.example.btl1.Repostories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.btl1.database.AppDatabase;
import com.example.btl1.database.dao.ResultDao;
import com.example.btl1.database.entity.ResultEntity;

import java.util.List;

public class ResultRepository {
    private ResultDao resultDao;
    private LiveData<List<ResultEntity>> allResults;

    public ResultRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        resultDao = database.resultDao();
        allResults = resultDao.getAllResults();
    }

    public void insert(ResultEntity result) {
        new InsertResultAsyncTask(resultDao).execute(result);
    }

    public LiveData<List<ResultEntity>> getAllResults() {
        return allResults;
    }

    public LiveData<ResultEntity> getResultById(String resultId) {
        return resultDao.getResultById(resultId);
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