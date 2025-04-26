package com.example.btl1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.btl1.database.entity.DetailResultEntity;

import java.util.List;

@Dao
public interface DetailResultDao {
    // Thêm một bản ghi DetailResultEntity vào bảng detail_results
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DetailResultEntity detailResult);

    // Lấy tất cả bản ghi từ bảng detail_results
    @Query("SELECT * FROM detail_results")
    List<DetailResultEntity> getAllDetails();

    // Lấy tất cả các chi tiết kết quả theo resultId
    @Query("SELECT * FROM detail_results WHERE resultId = :resultId")
    List<DetailResultEntity> getDetailsByResultId(String resultId);

    // Xóa chi tiết kết quả theo id
    @Query("DELETE FROM detail_results WHERE id = :detailResultId")
    void deleteDetailResult(int detailResultId);

    // Xóa tất cả các chi tiết kết quả theo resultId
    @Query("DELETE FROM detail_results WHERE resultId = :resultId")
    void deleteDetailsByResultId(String resultId);
}
