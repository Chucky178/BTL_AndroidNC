package com.example.btl1.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "results")
public class ResultEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private String examId;
    private String examName;
    private int score;
    private int totalQuestions;
    private long timestamp;
    private int timeCompleted;
    private String status;

    public ResultEntity(@NonNull String id, String examId, String examName, int score,
                        int totalQuestions, long timestamp, int timeCompleted, String status) {
        this.id = id;
        this.examId = examId;
        this.examName = examName;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.timestamp = timestamp;
        this.timeCompleted = timeCompleted;
        this.status = status;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getExamId() {
        return examId;
    }

    public String getExamName() {
        return examName;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getTimeCompleted() {
        return timeCompleted;
    }

    public String getStatus() {
        return status;
    }
}