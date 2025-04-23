package com.example.btl1.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.R;
import com.example.btl1.adapters.ResultDetailAdapter;
import com.example.btl1.models.ResultDetail;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private TextView scoreTextView;
    private RecyclerView recyclerView;
    private ResultDetailAdapter adapter;
    private List<ResultDetail> chiTietList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreTextView = findViewById(R.id.tvScore);
        //recyclerView = findViewById(R.id.recyclerViewResultDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        scoreTextView.setText(String.format("Điểm: %d/%d", score, totalQuestions));

        // Giả sử bạn gửi chi tiết kết quả qua Intent (có thể lấy từ Firebase nếu cần)
        // Ở đây, tôi giả định chi tiết đã được lưu và có thể truy xuất từ bảng ket_qua_thi
        chiTietList = new ArrayList<>();
        // Để lấy chi tiết, bạn có thể truy vấn Firebase dựa trên ma_ket_qua (cần gửi qua Intent)

        adapter = new ResultDetailAdapter(chiTietList);
        recyclerView.setAdapter(adapter);
    }
}