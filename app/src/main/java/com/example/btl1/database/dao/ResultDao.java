package com.example.btl1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.btl1.database.entity.ResultEntity;
import com.example.btl1.models.Result;

import java.util.List;

@Dao
public interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ResultEntity result);


    @Query("SELECT * FROM results")
    List<ResultEntity> getAllResults(); // Return a List instead of LiveData

    @Query("DELETE FROM results WHERE id = :resultId")
    void deleteResult(String resultId);
}