package com.example.btl1.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl1.R;
import com.example.btl1.Repostories.DetailResultRepository;
import com.example.btl1.adapters.DetailResultAdapter;
import com.example.btl1.database.entity.DetailResultEntity;
import com.example.btl1.database.entity.ResultEntity;
import com.example.btl1.Repostories.ResultRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TextView tvExamStatus, tvScore, tvTime;
    private Button btnRetry;
    private GridView gridView;

    private DetailResultAdapter adapter;
    private List<DetailResultEntity> detailResultList = new ArrayList<>();
    private DetailResultRepository detailRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvExamStatus = findViewById(R.id.tvExamStatus);
        tvScore = findViewById(R.id.tvScore);
        tvTime = findViewById(R.id.tvTime);
        btnRetry = findViewById(R.id.btnRetry);
        gridView = findViewById(R.id.gridViewQuestions);

        // Lấy thông tin từ Intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        int time = intent.getIntExtra("time", 0);
        String maKetQua = intent.getStringExtra("ma_ket_qua");

        // Hiển thị thông tin bài thi
        if (maKetQua != null) {
            loadExamInfo(time);
            loadDetailResults(maKetQua);
        }

        // Retry
        btnRetry.setOnClickListener(v -> retryExam());

        // Khi bấm vào từng câu hỏi
        gridView.setOnItemClickListener((adapterView, view, position, id) -> {
            DetailResultEntity result = detailResultList.get(position);
            loadQuestionFromFirebase(result.getQuestionId(), result.getSelectedAnswer());
        });
    }


    private void loadExamInfo(int time) {
        ResultRepository repository = new ResultRepository(getApplication());
        new Thread(() -> {
            ResultEntity resultEntity = repository.getResultsByTime(time);
            runOnUiThread(() -> {
                if (resultEntity != null) {
                    tvExamStatus.setText(resultEntity.getStatus());
                    tvScore.setText("Điểm số: " + resultEntity.getScore() + "/" + resultEntity.getTotalQuestions());
                    int minutes = resultEntity.getTimeCompleted() / 60;
                    int seconds = resultEntity.getTimeCompleted() % 60;
                    tvTime.setText("Thời gian hoàn thành: " + minutes + " phút " + seconds + " giây");
                } else {
                    tvExamStatus.setText("Không tìm thấy kết quả");
                }
            });
        }).start();
    }

    private void loadDetailResults(String maKetQua) {
        detailRepo = new DetailResultRepository(getApplication());
        detailRepo.getDetailsByResultId(maKetQua).observe(this, detailResultEntities -> {
            if (detailResultEntities != null && !detailResultEntities.isEmpty()) {
                detailResultList.clear();
                detailResultList.addAll(detailResultEntities);

                // Kiểm tra adapter có null không trước khi gọi notifyDataSetChanged()
                if (adapter == null) {
                    adapter = new DetailResultAdapter(ResultActivity.this, detailResultList);
                    gridView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


//    private void loadDetailResults(String maKetQua) {
//        detailRepo = new DetailResultRepository(getApplication());
//        new Thread(() -> {
//            detailResultList = detailRepo.getDetailsByResultId(maKetQua);
//            runOnUiThread(() -> {
//                adapter = new DetailResultAdapter(ResultActivity.this, detailResultList);
//                gridView.setAdapter(adapter);
//            });
//        }).start();
//    }

    private void loadQuestionFromFirebase(String maCauHoi, String luaChon) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("cau hoi");

        ref.orderByChild("ma_cau_hoi").equalTo(maCauHoi)
                .addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            String noiDung = child.child("noi_dung_cau_hoi").getValue(String.class);
                            String dapAnDung = child.child("dap_an_dung").getValue(String.class);
                            String giaiThich = child.child("giai_thich_cau_hoi").getValue(String.class);

                            String d1 = child.child("dap_an_1").getValue(String.class);
                            String d2 = child.child("dap_an_2").getValue(String.class);
                            String d3 = child.child("dap_an_3").getValue(String.class);
                            String d4 = child.child("dap_an_4").getValue(String.class);

                            String dapAnNguoiDung = luaChon;

                            StringBuilder builder = new StringBuilder();
                            builder.append("Câu hỏi: ").append(noiDung).append("\n\n");
                            builder.append("A. ").append(d1).append("\n");
                            builder.append("B. ").append(d2).append("\n");
                            if (d3 != null && !d3.isEmpty()) builder.append("C. ").append(d3).append("\n");
                            if (d4 != null && !d4.isEmpty()) builder.append("D. ").append(d4).append("\n\n");

                            builder.append("Bạn chọn: ").append(chuyenDapAn(luaChon)).append("\n");
                            builder.append("Đáp án đúng: ").append(chuyenDapAn(dapAnDung)).append("\n\n");
                            builder.append("Giải thích: ").append(giaiThich);

                            new AlertDialog.Builder(ResultActivity.this)
                                    .setTitle("Chi tiết câu hỏi")
                                    .setMessage(builder.toString())
                                    .setPositiveButton("Đóng", null)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(ResultActivity.this, "Lỗi tải dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String chuyenDapAn(String dapAn) {
        switch (dapAn) {
            case "dap_an_1":
                return "A";
            case "dap_an_2":
                return "B";
            case "dap_an_3":
                return "C";
            case "dap_an_4":
                return "D";
            default:
                return "Không xác định";
        }
    }

    private void retryExam() {
        Intent examIntent = new Intent(ResultActivity.this, ExamActivity.class);
        startActivity(examIntent);
        finish();
    }
}