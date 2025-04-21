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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.btl1.R;
import com.example.btl1.adapters.QuestionAdapter;
import com.example.btl1.adapters.QuestionPagerAdapter;
import com.example.btl1.models.Exam;
import com.example.btl1.models.Question;
import com.example.btl1.models.Result;
import com.example.btl1.models.ResultDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
public class ExamDetailActivity extends AppCompatActivity {
    private QuestionPagerAdapter questionPagerAdapter;
    private List<Question> questionList;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
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
        loadExamQuestions(maDe);
        findViewById(R.id.submitButton).setOnClickListener(v -> submitExam());
    }


    private void batDauDemGio(long totalTime) {
        countDownTimer = new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
                thoiGianHoanThanh = (totalTime - millisUntilFinished) / 1000 / 60; // tính phút đã làm
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Hết giờ!");
                submitExam(); // tự động nộp bài
            }
        }.start();
    }

    private void submitExam() {
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

//    private int tinhDiem() {
//        int diem = 0;
//
//        for (ResultDetail result : resultDetailList) {
//            // Tìm câu hỏi tương ứng
//            for (Question question : questionList) {
//                if (question.getMa_cau_hoi().equals(result.getMa_cau_hoi())) {
//                    // Lấy đáp án đúng từ mã (ví dụ "dap_an_2") => lấy nội dung
//                    String dapAnDungMa = question.getDap_an_dung(); // ví dụ "dap_an_2"
//                    String dapAnDungNoiDung = layNoiDungDapAn(question, dapAnDungMa); // ví dụ "Phần đường xe chạy"
//
//                    if (result.getDap_an_chon() != null && result.getDap_an_chon().equals(dapAnDungNoiDung)) {
//                        diem++;
//                    }
//                    break; // tìm thấy rồi thì không cần so tiếp
//                }
//            }
//        }
//
//        return diem;
//    }

//    private void loadExamQuestions(String maDe) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("cau hoi")
//                .whereEqualTo("ma_de", maDe)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        questionList = new ArrayList<>();
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Question question = document.toObject(Question.class);
//                            questionList.add(question);
//                            Log.d("cau hoi", "Danh sach cau hoi: " + question);
//
//                        }
//
//                        Log.d("cau hoi", "Tổng số câu hỏi: " + questionList.size());
//
//                        new Handler(Looper.getMainLooper()).post(() -> {
//                            questionPagerAdapter = new QuestionPagerAdapter(this, questionList);
//                            viewPager2.setAdapter(questionPagerAdapter);
//                        });
//                    } else {
//                        Log.d("ExamDetailActivity", "Error getting documents: ", task.getException());
//                    }
//                });
//    }

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


    private void luuKetQuaVaoFirebase() {
        // 1. Lưu thông tin tổng quan vào bảng ket_qua_thi
        DatabaseReference ketQuaRef = FirebaseDatabase.getInstance().getReference("ket_qua_thi");
        String maKetQua = ketQuaRef.push().getKey();
        Result ketQua = new Result();
        ketQua.setMa_ket_qua(maKetQua);
        ketQua.setMa_de(maDe);
        ketQua.setDiem_so(score);
        ketQua.setTong_so_cau(questionList.size());
        ketQua.setThoi_gian_hoan_thanh((int) thoiGianHoanThanh);
        ketQua.setNgay_lam(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
        ketQua.setTrang_thai(score >= questionList.size() / 2 ? "dung" : "sai");
        ketQuaRef.child(maKetQua).setValue(ketQua);

        // 2. Lưu chi tiết từng câu hỏi vào bảng chi_tiet_ket_qua
        DatabaseReference chiTietRef = FirebaseDatabase.getInstance().getReference("chi_tiet_ket_qua");
        for (ResultDetail q : resultDetail) {
            ResultDetail chiTiet = new ResultDetail();
            chiTiet.setMa_ket_qua(maKetQua);
            chiTiet.setMa_cau_hoi(q.getMa_cau_hoi());
            chiTiet.setDap_an_chon(q.getDap_an_chon());
            chiTiet.setDap_an_dung(q.getDap_an_dung());
            chiTiet.setTrang_thai(q.getDap_an_chon() != null && q.getDap_an_chon().equals(q.getDap_an_dung()) ? "dung" : "sai");

            String chiTietId = maKetQua + "_" + q.getMa_cau_hoi(); // Tạo ID duy nhất
            chiTietRef.child(chiTietId).setValue(chiTiet);
        }
    }
}