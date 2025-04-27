package com.example.btl1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.btl1.R;
import com.example.btl1.Repostories.DetailResultRepository;
import com.example.btl1.Repostories.ResultRepository;
import com.example.btl1.adapters.DetailResultAdapter;
import com.example.btl1.database.entity.DetailResultEntity;
import com.example.btl1.database.entity.ResultEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TextView tvExamStatus, tvScore, tvTime, tvName, tv_correct_count, tv_wrong_count, tv_unanswered_count;
    private Button btnRetry, btnViewHistory;
    private GridView gridView;

    private DetailResultAdapter adapter;
    private List<DetailResultEntity> detailResultList = new ArrayList<>();
    private DetailResultRepository detailRepo;

    private String maDeThi; // sẽ lấy từ database
    private String maKetQua; // để load chi tiết kết quả

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Ánh xạ view
        tvExamStatus = findViewById(R.id.tvExamStatus);
        tvScore = findViewById(R.id.tvScore);
        tvTime = findViewById(R.id.tvTime);
        btnRetry = findViewById(R.id.btnRetry);
        gridView = findViewById(R.id.gridViewQuestions);
        tvName = findViewById(R.id.tvExamName);
        tv_correct_count = findViewById(R.id.tv_correct_count);
        tv_wrong_count = findViewById(R.id.tv_wrong_count);
        tv_unanswered_count = findViewById(R.id.tv_unanswered_count);
        btnViewHistory = findViewById(R.id.btnViewHistory);
        getSupportActionBar().setTitle("Kết quả thi");

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        int time = intent.getIntExtra("time", 0);
        maKetQua = intent.getStringExtra("ma_ket_qua");

        if (maKetQua != null) {
            loadExamInfo();      // Load thông tin tổng quan
            loadDetailResults(maKetQua); // Load danh sách câu hỏi
        }

        btnRetry.setOnClickListener(v -> retryExam());
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });
        gridView.setOnItemClickListener((adapterView, view, position, id) -> {
            DetailResultEntity result = detailResultList.get(position);
            loadQuestionFromFirebase(result.getQuestionId(), result.getSelectedAnswer());
        });
    }

    private void loadExamInfo() {
        ResultRepository repository = new ResultRepository(getApplication());
        new Thread(() -> {
            ResultEntity resultEntity = repository.getResultById(maKetQua); // tìm theo ID kết quả
            runOnUiThread(() -> {
                if (resultEntity != null) {
                    tvExamStatus.setText(resultEntity.getStatus());

                    if (resultEntity.getStatus().equalsIgnoreCase("Đạt")) {
                        tvExamStatus.setTextColor(ContextCompat.getColor(ResultActivity.this, R.color.correct_answer));
                    } else {
                        tvExamStatus.setTextColor(ContextCompat.getColor(ResultActivity.this, R.color.wrong_answer));
                    }

                    tvScore.setText(resultEntity.getScore() + "/" + resultEntity.getTotalQuestions());

                    int minutes = resultEntity.getTimeCompleted() / 60;
                    int seconds = resultEntity.getTimeCompleted() % 60;
                    tvTime.setText(minutes + " phút " + seconds + " giây");

                    // Cập nhật đúng mã đề thi
                    maDeThi = resultEntity.getExamId();

                    // Gọi để lấy tên đề thi từ Firebase
                    loadExamNameFromFirebase(maDeThi);

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

                // Đếm số câu đúng/sai/chưa trả lời
                int correctCount = 0;
                int wrongCount = 0;
                int unansweredCount = 0;

                for (DetailResultEntity entity : detailResultEntities) {
                    String status = entity.getStatus();
                    if (status != null) {
                        switch (status.toLowerCase()) {
                            case "dung":
                                correctCount++;
                                break;
                            case "sai":
                                wrongCount++;
                                break;
                            case "chua tra loi":
                                unansweredCount++;
                                break;
                        }
                    }
                }

                // Cập nhật TextView
                tv_correct_count.setText("Đúng: " + correctCount);
                tv_wrong_count.setText("Sai: " + wrongCount);
                tv_unanswered_count.setText("Chưa trả lời: " + unansweredCount);

                if (adapter == null) {
                    adapter = new DetailResultAdapter(ResultActivity.this, detailResultList);
                    gridView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


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

                            StringBuilder builder = new StringBuilder();
                            builder.append("Câu hỏi: ").append(noiDung).append("\n\n")
                                    .append("A. ").append(d1).append("\n")
                                    .append("B. ").append(d2).append("\n");
                            if (d3 != null && !d3.isEmpty()) builder.append("C. ").append(d3).append("\n");
                            if (d4 != null && !d4.isEmpty()) builder.append("D. ").append(d4).append("\n\n");

                            builder.append("Bạn chọn: ").append(chuyenDapAn(luaChon)).append("\n")
                                    .append("Đáp án đúng: ").append(chuyenDapAn(dapAnDung)).append("\n\n")
                                    .append("Giải thích: ").append(giaiThich);

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
            case "dap_an_1": return "A";
            case "dap_an_2": return "B";
            case "dap_an_3": return "C";
            case "dap_an_4": return "D";
            default: return "Không xác định";
        }
    }

    private void retryExam() {
        if (maDeThi != null) {
            Intent examIntent = new Intent(ResultActivity.this, ExamDetailActivity.class);
            examIntent.putExtra("ma_de", maDeThi); // Truyền mã đề thi sang ExamActivity
            startActivity(examIntent);
            finish();
        } else {
            Toast.makeText(this, "Không tìm thấy mã đề thi để làm lại", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadExamNameFromFirebase(String maDe) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("de_thi");

        ref.orderByChild("ma_de").equalTo(maDe)
                .addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                String tenDe = child.child("ten_de").getValue(String.class);
                                if (tenDe != null) {
                                    tvName.setText(tenDe);
                                }
                            }
                        } else {
                            tvName.setText("Không tìm thấy đề thi");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(ResultActivity.this, "Lỗi tải tên đề thi", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
