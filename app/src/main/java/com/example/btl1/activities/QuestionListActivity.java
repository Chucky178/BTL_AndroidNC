package com.example.btl1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.adapters.QuestionAdapter;
import com.example.btl1.Repostories.QuestionRepository;
import com.example.btl1.R;
import com.example.btl1.models.Question;

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


        // Nhận mã nhóm câu hỏi từ Intent
        Intent intent = getIntent();
        String maNhomCauHoi = intent.getStringExtra("ma_nhom_cau_hoi");

        // Nếu mã nhóm câu hỏi có giá trị, tải câu hỏi tương ứng
        if (maNhomCauHoi != null) {
            // Gọi phương thức loadQuestions để lấy dữ liệu câu hỏi
            loadQuestions(maNhomCauHoi);
        } else {
            Toast.makeText(this, "Không có mã nhóm câu hỏi!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadQuestions(String maNhomCauHoi) {
        // Gọi QuestionRepository để lấy danh sách câu hỏi với callback
        QuestionRepository.getQuestionsByTopic(maNhomCauHoi, new QuestionRepository.DataCallback<List<Question>>() {
            @Override
            public void onDataLoaded(List<Question> data) {
                // Khi dữ liệu đã được tải thành công
                if (data != null && !data.isEmpty()) {
                    questionList = data;
                    questionAdapter = new QuestionAdapter(QuestionListActivity.this, questionList);
                    recyclerView.setAdapter(questionAdapter);
                    questionAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(QuestionListActivity.this, "Không có câu hỏi nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataError(Exception exception) {
                // Nếu có lỗi khi tải dữ liệu
                Toast.makeText(QuestionListActivity.this, "Lỗi khi tải câu hỏi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
