package com.example.btl1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl1.R;
import com.example.btl1.Repostories.ResultRepository;
import com.example.btl1.database.entity.ResultEntity;
import com.example.btl1.models.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity {
    private TextView tvExamName, tvScore, tvCorrectAnswers, tvTime;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Khởi tạo các view
        initViews();

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        String maKetQua = intent.getStringExtra("ma_ket_qua");
        int time = intent.getIntExtra("time", 0);




        if (maKetQua != null) {
            loadExamName(time);
        }

        // Xử lý nút "Thi lại"
        btnRetry.setOnClickListener(v -> retryExam());
    }

    private void initViews() {
        tvExamName = findViewById(R.id.tvExamName);
        tvScore = findViewById(R.id.tvScore);
        tvCorrectAnswers = findViewById(R.id.tvCorrectAnswers);
        tvTime = findViewById(R.id.tvTime);
        btnRetry = findViewById(R.id.btnRetry);
    }

//    private void displayBasicInfo(int score, int totalQuestions) {
//        tvScore.setText(String.format("Điểm số: %d", score));
//        tvCorrectAnswers.setText(String.format("Số câu đúng: %d/%d", score, totalQuestions));
//    }


    private void loadExamName(int time) {
        // Create a ResultRepository instance
        ResultRepository repository = new ResultRepository(getApplication());

        // Fetch the exam result from the Room database
        new Thread(() -> {
            ResultEntity resultEntity = repository.getResultsByTime(time);
            runOnUiThread(() -> {
                if (resultEntity != null) {
                    tvExamName.setText(resultEntity.getExamName());
                    tvTime.setText(String.format("Thời gian hoàn thành: %d phút %d giây",
                            resultEntity.getTimeCompleted() / 60, resultEntity.getTimeCompleted() % 60));
                    tvScore.setText(String.format("Điểm số: %d/%d", resultEntity.getScore(), resultEntity.getTotalQuestions()));
                } else {
                    tvExamName.setText("Không tìm thấy kết quả");
                }
            });
        }).start();
    }

    private void displayCompletionTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        tvTime.setText(String.format("Thời gian hoàn thành: %d phút %d giây", minutes, remainingSeconds));
    }

    private void retryExam() {
        Intent examIntent = new Intent(ResultActivity.this, ExamActivity.class);
        startActivity(examIntent);
        finish();
    }
}