package com.example.btl1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.btl1.R;
import com.example.btl1.Repostories.ResultRepository;
import com.example.btl1.adapters.QuestionPagerAdapter;
import com.example.btl1.database.entity.ResultEntity;
import com.example.btl1.models.Question;
import com.example.btl1.models.Result;
import com.example.btl1.models.ResultDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExamDetailActivity extends AppCompatActivity {
    private QuestionPagerAdapter questionPagerAdapter;
    private List<Question> questionList;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 1140000; // 19 minutes
    private String maDe;
    private int score;
    private long thoiGianHoanThanh;

    private List<ResultDetail> resultDetail;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);

        viewPager2 = findViewById(R.id.viewPagerQuestions);
        timerTextView = findViewById(R.id.tvTimer);

        maDe = getIntent().getStringExtra("ma_de");
        Log.d("loadExamQuestions", "Mã đề: " + maDe);

        resultDetail = new ArrayList<>();

        startTimer();
        loadExamQuestions(maDe);

        findViewById(R.id.submitButton).setOnClickListener(v -> submitExam());
    }

    private void loadExamQuestions(String maDe) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("cau hoi");
        ref.orderByChild("ma_de").equalTo(maDe)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        questionList = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Question question = ds.getValue(Question.class);
                            if (question != null) {
                                questionList.add(question);
                                Log.d("cau hoi", "Câu hỏi: " + question.getNoiDungCauHoi());
                            }
                        }

                        Log.d("cau hoi", "Tổng số câu hỏi: " + questionList.size());

                        if (questionList.isEmpty()) {
                            Toast.makeText(ExamDetailActivity.this, "Không có câu hỏi nào.", Toast.LENGTH_SHORT).show();
                        } else {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                questionPagerAdapter = new QuestionPagerAdapter(ExamDetailActivity.this, questionList);
                                viewPager2.setAdapter(questionPagerAdapter);
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ExamDetailActivity.this, "Lỗi tải câu hỏi", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startTimer() {
        // Đảm bảo timeLeftInMillis được khởi tạo trước khi bắt đầu
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { // 1000ms = 1s
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;  // Cập nhật thời gian còn lại
                updateTimerText();  // Cập nhật hiển thị đồng hồ
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Hết giờ!");  // Khi hết giờ
                // TODO: Xử lý nộp bài tự động tại đây
            }
        }.start();
    }

    private void updateTimerText() {
        // Tính toán phút và giây từ timeLeftInMillis
        int minutes = (int) (timeLeftInMillis / 1000) / 60;  // Lấy số phút
        int seconds = (int) (timeLeftInMillis / 1000) % 60;  // Lấy số giây còn lại

        // Định dạng lại thời gian để hiển thị dưới dạng mm:ss
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);  // Hiển thị lên UI
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    private void submitExam() {
        resultDetail = new ArrayList<>();
        score = 0; // Đặt lại điểm số về 0

        // Tính điểm và tạo chi tiết kết quả
        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);
            String dapAnChon = questionPagerAdapter.getUserAnswer(i);
            String dapAnDung = q.getDapAnDung();

            ResultDetail rd = new ResultDetail();
            rd.setMa_cau_hoi(q.getMaCauHoi());
            rd.setDap_an_chon(dapAnChon);
            rd.setDap_an_dung(dapAnDung);

            if (dapAnChon == null) {
                rd.setTrang_thai("chua tra loi");
            } else if (dapAnChon.equals(dapAnDung)) {
                rd.setTrang_thai("dung");
                score++; // Tăng điểm chỉ một lần ở đây
            } else {
                rd.setTrang_thai("sai");
            }

            resultDetail.add(rd);
        }

        countDownTimer.cancel();
        luuKetQuaThi();
    }

    private void saveResultToRoomDatabase(Result result, String examName) {
        // Create a ResultEntity from the Result object
        ResultEntity resultEntity = new ResultEntity(
                result.getMa_ket_qua(),
                result.getMa_de(),
                examName,
                result.getDiem_so(),
                result.getTong_so_cau(),
                System.currentTimeMillis(),
                result.getThoi_gian_hoan_thanh(),
                result.getTrang_thai()
        );

        // Create repository and save result
        ResultRepository repository = new ResultRepository(getApplication());
        repository.insert(resultEntity);
    }

    private void luuKetQuaThi() {
        // Create result ID
        String maKetQua = "KQ_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        // Calculate completion time in seconds
        int thoiGianHoanThanhGiay = (int) ((19 * 60 * 1000 - timeLeftInMillis) / 1000);

        // Create Result object
        Result result = new Result();
        result.setMa_ket_qua(maKetQua);
        result.setMa_de(maDe);
        result.setDiem_so(score);
        result.setTong_so_cau(questionList.size());
        result.setThoi_gian_hoan_thanh(thoiGianHoanThanhGiay);
        result.setNgay_lam(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(new Date()));
        result.setTrang_thai(score >= 21 ? "Đạt" : "Không đạt");

        // Set result details
        Map<String, ResultDetail> chiTietCauHoi = new HashMap<>();
        for (ResultDetail detail : resultDetail) {
            chiTietCauHoi.put(detail.getMa_cau_hoi(), detail);
        }
        result.setChi_tiet_cau_hoi(chiTietCauHoi);

        // Get exam name to save to Room database
        DatabaseReference examRef = FirebaseDatabase.getInstance().getReference("de_thi").child(maDe);
        examRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String examName = "Bài thi";
                if (snapshot.exists() && snapshot.child("ten_de").exists()) {
                    examName = snapshot.child("ten_de").getValue(String.class);
                }

                // Save to Room database only
                saveResultToRoomDatabase(result, examName);

                Toast.makeText(ExamDetailActivity.this, "Đã lưu kết quả bài thi vào cơ sở dữ liệu local!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ExamDetailActivity.this, ResultActivity.class);
                intent.putExtra("ma_ket_qua", maKetQua);
                intent.putExtra("score", score);
                intent.putExtra("totalQuestions", questionList.size());
                intent.putExtra("time", thoiGianHoanThanhGiay);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // If we can't get the exam name, save with default name
                saveResultToRoomDatabase(result, "Bài thi");

//                Toast.makeText(ExamDetailActivity.this, "Đã lưu kết quả bài thi vào cơ sở dữ liệu local!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ExamDetailActivity.this, ResultActivity.class);
                intent.putExtra("ma_ket_qua", maKetQua);
                intent.putExtra("score", score);
                intent.putExtra("totalQuestions", questionList.size());
                startActivity(intent);
                finish();
            }
        });
    }


    private String layNoiDungDapAn(Question question, String dapAnDung) {
        switch (dapAnDung) {
            case "dap_an_1": return question.getDapAn1();
            case "dap_an_2": return question.getDapAn2();
            case "dap_an_3": return question.getDapAn3();
            case "dap_an_4": return question.getDapAn4();
            default: return "";
        }
    }
}
