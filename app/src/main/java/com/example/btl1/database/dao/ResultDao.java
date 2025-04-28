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
    List<ResultEntity> getAllResults(); // Trả về một list thay vì LiveData

    @Query("Select * from results where timeCompleted= :time limit 1")
    ResultEntity getResultsByTime(int time);

    @Query("DELETE FROM results WHERE id = :resultId")
    void deleteResult(String resultId); // xoa theo id

    @Query("SELECT * FROM results WHERE id = :maKetQua LIMIT 1")
    ResultEntity getResultById(String maKetQua); //lay theo id

    // Xóa tất cả các bản ghi trong bảng result
    @Query("DELETE FROM results")
    void deleteAllResults();
}