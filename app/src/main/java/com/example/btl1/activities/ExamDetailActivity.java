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
import com.example.btl1.adapters.QuestionPagerAdapter;
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
    private long timeLeftInMillis = 1140000;
    private String maDe;
    private int score; // điểm số
    private long thoiGianHoanThanh; // thời gian đã làm bài, đơn vị: phút

    private List<ResultDetail> resultDetail;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);
        viewPager2 = findViewById(R.id.viewPagerQuestions);
        maDe = getIntent().getStringExtra("ma_de");
        Log.d("loadExamQuestions", "Mã đề: " + maDe);
        resultDetail = new ArrayList<>();
        startTimer();
        loadExamQuestions(maDe);
        timerTextView = findViewById(R.id.tvTimer);
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
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Hết giờ!");
                // TODO: Xử lý nộp bài tự động tại đây
            }
        }.start();
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
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
        score = 0;

        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);
            String dapAnChon = questionPagerAdapter.getUserAnswer(i);
            String dapAnDung = q.getDapAnDung();

            // Chuyển mã đáp án đúng (ví dụ: "dap_an_2") thành nội dung thực tế
            String noiDungDapAnDung = layNoiDungDapAn(q, dapAnDung);

            if (dapAnChon != null && dapAnChon.equals(noiDungDapAnDung)) {
                score++;
            }

            ResultDetail rd = new ResultDetail();
            rd.setMa_cau_hoi(q.getMaCauHoi());
            rd.setDap_an_chon(dapAnChon);
            rd.setDap_an_dung(noiDungDapAnDung);
            resultDetail.add(rd);
        }

        countDownTimer.cancel();
        //  score = tinhDiem();
        luuKetQuaVaoFirebase();
        Intent intent = new Intent(ExamDetailActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", questionList.size());
        intent.putExtra("maKetQua", "kq001"); // Gửi ma_ket_qua để ResultActivity truy vấn chi tiết
        startActivity(intent);
        finish();
    }
    private void luuKetQuaVaoFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("ket_qua_thi");

        // Tạo mã kết quả mới
        String maKetQua = reference.push().getKey();

        // Tạo đối tượng Result
        Result result = new Result();
        result.setMa_ket_qua(maKetQua);
        result.setMa_de("DE01");  // Ví dụ mã đề thi
        result.setDiem_so(0);
        result.setTong_so_cau(questionList.size());
        result.setThoi_gian_hoan_thanh(0);
        result.setNgay_lam(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(new Date()));
        result.setTrang_thai("sai");

        // Lưu chi tiết câu hỏi
        Map<String, ResultDetail> chiTietCauHoi = new HashMap<>();
        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);
            String dapAnChon = questionPagerAdapter.getUserAnswer(i);  // Lấy đáp án người dùng chọn
            String dapAnDung = q.getDapAnDung();

            ResultDetail resultDetail = new ResultDetail();
            resultDetail.setMa_ket_qua(maKetQua);
            resultDetail.setMa_cau_hoi(q.getMaCauHoi());
            resultDetail.setDap_an_chon(dapAnChon);
            resultDetail.setDap_an_dung(dapAnDung);
            resultDetail.setTrang_thai(dapAnChon.equals(dapAnDung) ? "duong" : "sai");

            chiTietCauHoi.put(q.getMaCauHoi(), resultDetail);
        }
        result.setChi_tiet_cau_hoi(chiTietCauHoi);

        // Lưu vào Firebase
        reference.child(maKetQua).setValue(result);
    }
}