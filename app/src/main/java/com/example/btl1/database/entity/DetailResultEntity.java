package com.example.btl1.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "detail_results",
        foreignKeys = @ForeignKey(entity = ResultEntity.class,
                parentColumns = "id",
                childColumns = "resultId",
                onDelete = ForeignKey.CASCADE))
public class DetailResultEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;  // Primary key

    @NonNull
    private String resultId;  // Foreign key liên kết với ResultEntity
    private String questionId;  // ID câu hỏi
    private String selectedAnswer;  // Câu trả lời đã chọn
    private String status;  // Trạng thái câu trả lời (ví dụ: đúng/sai)

    // Constructor
    public DetailResultEntity(@NonNull String resultId, String questionId,
                              String selectedAnswer, String status) {
        this.resultId = resultId;
        this.questionId = questionId;
        this.selectedAnswer = selectedAnswer;
        this.status = status;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getResultId() {
        return resultId;
    }

    public void setResultId(@NonNull String resultId) {
        this.resultId = resultId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
