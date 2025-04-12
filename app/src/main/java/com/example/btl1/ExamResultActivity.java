package com.example.btl1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExamResultActivity extends AppCompatActivity {
    private TextView tvExamName, tvScore, tvCorrectAnswers, tvTime;
    private Button btnRetry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);

        tvExamName = findViewById(R.id.tvExamName);
        tvScore = findViewById(R.id.tvScore);
        tvCorrectAnswers = findViewById(R.id.tvCorrectAnswers);
        tvTime = findViewById(R.id.tvTime);
        btnRetry = findViewById(R.id.btnRetry);

        // Nhận dữ liệu từ Intent
        ExamResult result = (ExamResult) getIntent().getSerializableExtra("exam_result");

        if (result != null) {
            tvExamName.setText(result.getExamName());
            tvScore.setText("Điểm số: " + result.getScore());
            tvCorrectAnswers.setText("Số câu đúng: " + result.getCorrectAnswers() + "/" + result.getTotalQuestions());

            // Chuyển timestamp (long) thành đối tượng Date
            Date date = new Date(result.getTimestamp());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            String formattedTime = sdf.format(date);

            tvTime.setText("Thời gian: " + formattedTime);
        }

        // Nút thi lại
        btnRetry.setOnClickListener(v -> {
            Intent intent = new Intent(ExamResultActivity.this, ExamActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
