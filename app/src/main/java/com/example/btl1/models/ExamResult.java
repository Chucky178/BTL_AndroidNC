package com.example.btl1.models;

import java.io.Serializable;

public class ExamResult implements Serializable {
    private String examId;
    private int correctAnswers;
    private int totalQuestions;
    private Long timestamp;

    public ExamResult() {}

    public ExamResult(String examId, int correctAnswers, int totalQuestions, Long timestamp) {
        this.examId = examId;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.timestamp = timestamp;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getExamName() {
        return "Đề thi " + examId;
    }

    public String getScore() {
        return correctAnswers + "/" + totalQuestions;
    }
}
