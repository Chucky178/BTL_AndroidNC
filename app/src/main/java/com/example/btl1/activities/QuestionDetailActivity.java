package com.example.btl1.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.btl1.R;
import com.example.btl1.adapters.QuestionDetailAdapter;
import com.example.btl1.models.Question;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class QuestionDetailActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private QuestionDetailAdapter adapter;
    private List<Question> questionList;
    private TabLayout tabLayoutQuestions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        // Ánh xạ View
        viewPager = findViewById(R.id.viewPagerQuestions);
        tabLayoutQuestions = findViewById(R.id.tabLayoutQuestions);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        questionList = (List<Question>) intent.getSerializableExtra("cau hoi");

        if (questionList == null || questionList.isEmpty()) {
            finish();
            return;
        }

        // Cài đặt ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Câu hỏi");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Thiết lập Adapter cho ViewPager2
        adapter = new QuestionDetailAdapter(this, questionList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position, false);

        // Kết nối TabLayout và ViewPager2
        new TabLayoutMediator(tabLayoutQuestions, viewPager, (tab, tabPosition) -> {
            Question question = questionList.get(tabPosition);
            tab.setText(question.getThuTu());
        }).attach();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
