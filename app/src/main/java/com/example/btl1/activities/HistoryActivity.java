package com.example.btl1.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.R;
import com.example.btl1.Repostories.ResultRepository;
import com.example.btl1.adapters.HistoryAdapter;
import com.example.btl1.database.entity.ResultEntity;
import com.example.btl1.models.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<Result> resultList;
    private TextView tvEmpty, tvHistoryCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewHistory);
        tvEmpty = findViewById(R.id.tvEmpty);
        tvHistoryCount = findViewById(R.id.tvHistoryCount);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultList = new ArrayList<>();
        adapter = new HistoryAdapter(this, resultList);
        recyclerView.setAdapter(adapter);

        // Set item click listener
//        adapter.setOnItemClickListener(result ->
////                Toast.makeText(this, "Đã chọn kết quả: " + result.getMa_de(), Toast.LENGTH_SHORT).show()
//        );

        // Load exam history
        loadExamHistory();
    }

    private void loadExamHistory() {
        new Thread(() -> {
            ResultRepository repository = new ResultRepository(getApplication());
            List<ResultEntity> results = repository.getAllResults(); // Fetch all results from Room

            runOnUiThread(() -> {
                resultList.clear();
                if (results.isEmpty()) {
                    showEmptyMessage("Chưa có lịch sử thi");
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

                    // Đảo ngược danh sách results
                    Collections.reverse(results);
                    for (ResultEntity entity : results) {
                        // Map ResultEntity to Result
                        Result result = new Result();
                        result.setMa_ket_qua(entity.getId());
                        result.setMa_de(entity.getExamId());
                        result.setDiem_so(entity.getScore());
                        result.setTong_so_cau(entity.getTotalQuestions());
                        result.setThoi_gian_hoan_thanh(entity.getTimeCompleted());
                        result.setNgay_lam(dateFormat.format(new Date(entity.getTimestamp()))); // Format timestamp
                        result.setTrang_thai(entity.getStatus());
                        resultList.add(result);
                    }
                    hideEmptyMessage();
                    adapter.notifyDataSetChanged();
                    // Cập nhật số lượng lịch sử thi
                    int itemCount = adapter.getItemCount();
                    tvHistoryCount.setText("Số lượng lịch sử thi: " + itemCount);
                }
            });
        }).start();
    }

    private void showEmptyMessage(String message) {
        tvEmpty.setText(message);
        tvEmpty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideEmptyMessage() {
        tvEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}