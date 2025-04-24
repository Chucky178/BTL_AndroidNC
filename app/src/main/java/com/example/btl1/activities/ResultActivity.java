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
import com.example.btl1.models.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    private TextView tvExamName, tvScore, tvCorrectAnswers, tvTime;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Khởi tạo các view
        tvExamName = findViewById(R.id.tvExamName);
        tvScore = findViewById(R.id.tvScore);
        tvCorrectAnswers = findViewById(R.id.tvCorrectAnswers);
        tvTime = findViewById(R.id.tvTime);
        btnRetry = findViewById(R.id.btnRetry);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        String maKetQua = intent.getStringExtra("ma_ket_qua");

        // Hiển thị dữ liệu cơ bản
        tvScore.setText(String.format("Điểm số: %d", score));
        tvCorrectAnswers.setText(String.format("Số câu đúng: %d/%d", score, totalQuestions));

        // Lấy thêm thông tin chi tiết từ Firebase
        if (maKetQua != null) {
            loadResultDetails(maKetQua);
        }

        // Xử lý nút "Thi lại"
        btnRetry.setOnClickListener(v -> {
            Intent examIntent = new Intent(ResultActivity.this, ExamActivity.class);
            startActivity(examIntent);
            finish();
        });
    }

    private void loadResultDetails(String maKetQua) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ket_qua_thi").child(maKetQua);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Result result = snapshot.getValue(Result.class);
                    if (result != null) {
                        // Lấy tên đề thi từ mã đề
                        loadExamName(result.getMa_de());

                        // Hiển thị thời gian hoàn thành
                        int seconds = result.getThoi_gian_hoan_thanh();
                        int minutes = seconds / 60;
                        int remainingSeconds = seconds % 60;
                        tvTime.setText(String.format("Thời gian hoàn thành: %d phút %d giây", minutes, remainingSeconds));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ResultActivity.this, "Lỗi khi tải dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadExamName(String maDe) {
        DatabaseReference examRef = FirebaseDatabase.getInstance().getReference("de_thi").child(maDe);
        examRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.child("ten_de").exists()) {
                    String tenDe = snapshot.child("ten_de").getValue(String.class);
                    tvExamName.setText(tenDe != null ? tenDe : "Bài thi");
                } else {
                    tvExamName.setText("Bài thi");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ResultActivity", "Error loading exam name", error.toException());
                tvExamName.setText("Bài thi");
            }
        });
    }
}