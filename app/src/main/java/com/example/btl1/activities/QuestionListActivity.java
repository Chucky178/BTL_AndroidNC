package com.example.btl1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.adapters.QuestionAdapter;
import com.example.btl1.Repostories.QuestionRepository;
import com.example.btl1.R;
import com.example.btl1.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        recyclerView = findViewById(R.id.recyclerViewQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

// Cài đặt ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Danh sách câu hỏi");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        questionList = new ArrayList<>();

        // Khởi tạo adapter và gắn vào RecyclerView
        questionAdapter = new QuestionAdapter(this, questionList);
        recyclerView.setAdapter(questionAdapter);

        boolean hienTatCa = getIntent().getBooleanExtra("hien_tat_ca", false);
        boolean hienDiemLiet = getIntent().getBooleanExtra("hien_diem_liet", false);
        String maNhomCauHoi = getIntent().getStringExtra("ma_nhom_cau_hoi");
        String tenNhom = getIntent().getStringExtra("ten_nhom");
        getSupportActionBar().setTitle(tenNhom);

        if (hienTatCa) {
            loadAllQuestions();
        } else if (hienDiemLiet) {
            loadDiemLietQuestions();
        } else if (maNhomCauHoi != null) {
            loadQuestionsByTopic(maNhomCauHoi);
        } else {
            Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
        }
    }


    // Phương thức tải tất cả câu hỏi
    // Phương thức tải tất cả câu hỏi
    private void loadAllQuestions() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("cau hoi");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Thêm câu hỏi vào danh sách chung
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    if (question != null) {
                        questionList.add(question);
                    }
                }
                Log.d("QuestionListActivity", "Câu hỏi đã được tải: " + questionList.size());// Thêm câu hỏi vào danh sách đã có

                // Cập nhật RecyclerView với câu hỏi đã tải
                if (!questionList.isEmpty()) {
                    questionAdapter.notifyDataSetChanged();  // Thông báo adapter cập nhật lại
                } else {
                    Toast.makeText(QuestionListActivity.this, "Không có câu hỏi nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(QuestionListActivity.this, "Lỗi khi tải tất cả câu hỏi", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Phương thức tải câu hỏi điểm liệt
    private void loadDiemLietQuestions() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("cau hoi");

        reference.orderByChild("is_cau_diem_liet").equalTo("1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Thêm câu hỏi điểm liệt vào danh sách chung
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Question question = snapshot.getValue(Question.class);
                            if (question != null) {
                                questionList.add(question);
                            }
                        }
                        Log.d("QuestionListActivity", "Câu hỏi đã được tải: " + questionList.size());// Thêm câu hỏi vào danh sách đã có

                        // Cập nhật RecyclerView với câu hỏi điểm liệt đã tải
                        if (!questionList.isEmpty()) {
                            questionAdapter.notifyDataSetChanged();  // Thông báo adapter cập nhật lại
                        } else {
                            Toast.makeText(QuestionListActivity.this, "Không có câu hỏi điểm liệt", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(QuestionListActivity.this, "Lỗi khi tải câu hỏi điểm liệt", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void loadQuestionsByTopic(String maNhomCauHoi) {
        final List<Question> topicQuestions = new ArrayList<>();

        // Tải câu hỏi theo topic
        Log.d("QuestionListActivity", "Tải câu hỏi theo topic...");
        QuestionRepository.getQuestionsByTopic(maNhomCauHoi, new QuestionRepository.DataCallback<List<Question>>() {
            @Override
            public void onDataLoaded(List<Question> data) {
                if (data != null && !data.isEmpty()) {
                    topicQuestions.addAll(data);
                    Log.d("QuestionListActivity", "Câu hỏi theo topic đã được tải: " + data.size());
                }

                // Hiển thị câu hỏi theo topic vào RecyclerView
                if (!topicQuestions.isEmpty()) {
                    questionAdapter = new QuestionAdapter(QuestionListActivity.this, topicQuestions);
                    recyclerView.setAdapter(questionAdapter);
                    questionAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(QuestionListActivity.this, "Không có câu hỏi nào trong topic này", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataError(Exception exception) {
                Log.e("QuestionListActivity", "Lỗi khi tải câu hỏi theo topic", exception);
                Toast.makeText(QuestionListActivity.this, "Lỗi khi tải câu hỏi theo topic", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
