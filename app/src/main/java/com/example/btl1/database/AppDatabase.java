package com.example.btl1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.btl1.database.dao.ResultDao;
import com.example.btl1.database.dao.DetailResultDao;  // Thêm import cho DAO của DetailResult
import com.example.btl1.database.entity.ResultEntity;
import com.example.btl1.database.entity.DetailResultEntity;  // Thêm import cho entity của DetailResult

@Database(entities = {ResultEntity.class, DetailResultEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract ResultDao resultDao();
    public abstract DetailResultDao detailResultDao();  // Thêm phương thức DAO cho DetailResultEntity

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "result_database")
                    .fallbackToDestructiveMigration() // Chấp nhận xóa cơ sở dữ liệu cũ nếu có thay đổi
                    .build();
        }
        return instance;
    }
}

