package com.example.btl1.Repostories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.btl1.database.AppDatabase;
import com.example.btl1.database.dao.DetailResultDao;
import com.example.btl1.database.entity.DetailResultEntity;

import java.util.List;

public class DetailResultRepository {
    private DetailResultDao detailResultDao;

    public DetailResultRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        detailResultDao = db.detailResultDao();  // Truy cập vào DAO của DetailResultEntity
    }

    // Phương thức thêm dữ liệu vào bảng detail_results
    public void insert(DetailResultEntity detailResult) {
        new InsertDetailResultAsyncTask(detailResultDao).execute(detailResult);
    }

public LiveData<List<DetailResultEntity>> getDetailsByResultId(String resultId) {
    return detailResultDao.getDetailsByResultId(resultId);
}


    // Phương thức xóa các chi tiết kết quả theo resultId
    public void deleteDetailsByResultId(String resultId) {
        new DeleteDetailResultAsyncTask(detailResultDao).execute(resultId);
    }

    // AsyncTask để chèn dữ liệu vào bảng detail_results
    private static class InsertDetailResultAsyncTask extends AsyncTask<DetailResultEntity, Void, Void> {
        private DetailResultDao detailResultDao;

        private InsertDetailResultAsyncTask(DetailResultDao detailResultDao) {
            this.detailResultDao = detailResultDao;
        }

        @Override
        protected Void doInBackground(DetailResultEntity... detailResultEntities) {
            detailResultDao.insert(detailResultEntities[0]);
            return null;
        }
    }

    // AsyncTask để xóa dữ liệu trong bảng detail_results == result
    private static class DeleteDetailResultAsyncTask extends AsyncTask<String, Void, Void> {
        private DetailResultDao detailResultDao;

        private DeleteDetailResultAsyncTask(DetailResultDao detailResultDao) {
            this.detailResultDao = detailResultDao;
        }

        @Override
        protected Void doInBackground(String... resultIds) {
            detailResultDao.deleteDetailsByResultId(resultIds[0]);
            return null;
        }
    }
    // Xóa tất cả các bản ghi trong bảng detailresult
    public void deleteAllDetailResults() {
        detailResultDao.deleteAllDetailResults(); // Phương thức xóa trong DAO của bảng detailresult
    }

}