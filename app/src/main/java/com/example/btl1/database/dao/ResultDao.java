package com.example.btl1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.btl1.database.entity.ResultEntity;

import java.util.List;

@Dao
public interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ResultEntity result);

    @Query("SELECT * FROM results ORDER BY timestamp DESC")
    LiveData<List<ResultEntity>> getAllResults();

    @Query("SELECT * FROM results WHERE id = :resultId")
    LiveData<ResultEntity> getResultById(String resultId);

    @Query("DELETE FROM results WHERE id = :resultId")
    void deleteResult(String resultId);
}