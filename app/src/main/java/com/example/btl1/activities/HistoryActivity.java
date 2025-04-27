package com.example.btl1.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.R;
import com.example.btl1.Repostories.DetailResultRepository;
import com.example.btl1.Repostories.ResultRepository;
import com.example.btl1.adapters.HistoryAdapter;
import com.example.btl1.database.entity.ResultEntity;
import com.example.btl1.fragments.MenuFragment;
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
    private TextView btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
// Thêm MenuFragment vào Activity
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menuContainer, new MenuFragment());  // menuContainer là container để chứa fragment
            transaction.commit();
        }

        getSupportActionBar().setTitle("Ôn thi GPLX A1 - Lịch sử thi");
        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewHistory);
        tvEmpty = findViewById(R.id.tvEmpty);
        tvHistoryCount = findViewById(R.id.tvHistoryCount);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultList = new ArrayList<>();
        adapter = new HistoryAdapter(this, resultList);
        recyclerView.setAdapter(adapter);
        btnDelete = findViewById(R.id.btnDeleteHistory);
        // Set item click listener
//        adapter.setOnItemClickListener(result ->
////                Toast.makeText(this, "Đã chọn kết quả: " + result.getMa_de(), Toast.LENGTH_SHORT).show()
//        );

        // Load exam history
        loadExamHistory();
        // Thiết lập click listener cho nút xóa
        btnDelete.setOnClickListener(view -> showDeleteConfirmationDialog());
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

                    // Cập nhật số lượng lịch sử thi sau khi làm mới danh sách
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

    private void showDeleteConfirmationDialog() {
        // Tạo hộp thoại xác nhận
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc chắn muốn xóa tất cả lịch sử thi không?")
                .setPositiveButton("Có", (dialog, which) -> deleteHistory())
                .setNegativeButton("Không", null)
                .show();
    }

    private void deleteHistory() {
        new Thread(() -> {
            ResultRepository resultRepository = new ResultRepository(getApplication());
            DetailResultRepository detailResultRepository = new DetailResultRepository(getApplication());

            // Xóa tất cả các bản ghi trong bảng detailresult trước
            detailResultRepository.deleteAllDetailResults();

            // Xóa tất cả các bản ghi trong bảng result sau khi đã xóa xong bảng detailresult
            resultRepository.deleteAllResults();

            runOnUiThread(() -> {
                // Sau khi xóa xong, làm sạch danh sách kết quả và cập nhật lại giao diện
                resultList.clear(); // Làm sạch danh sách kết quả
                adapter.notifyDataSetChanged(); // Cập nhật lại RecyclerView

                // Cập nhật số lượng lịch sử thi (sẽ là 0 sau khi xóa)
                tvHistoryCount.setText("Số lượng lịch sử thi: 0");

                // Hiển thị thông báo xóa thành công
                Toast.makeText(HistoryActivity.this, "Đã xóa tất cả lịch sử thi", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }




}