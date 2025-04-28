package com.example.btl1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl1.R;
import com.example.btl1.adapters.ExamAdapter;
import com.example.btl1.adapters.QuestionAdapter;
import com.example.btl1.fragments.MenuFragment;
import com.example.btl1.models.Exam;
import com.example.btl1.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
public class ExamActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Exam> examList;
    private ExamAdapter examAdapter;
    private List<Question> questionList;
    private QuestionAdapter questionAdapter;  // Chú ý sửa kiểu dữ liệu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        // Thêm MenuFragment vào Activity
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menuContainer, new MenuFragment());  // menuContainer là container để chứa fragment
            transaction.commit();
        }
        getSupportActionBar().setTitle("Ôn thi GPLX A1 - Đề thi thử ");

        recyclerView = findViewById(R.id.rvDeThi);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        examList = new ArrayList<>();
        examAdapter = new ExamAdapter(this, examList, new ExamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String maDe) {
                // Khi nhấn vào đề thi, truyền maDe vào intent
                Intent intent = new Intent(ExamActivity.this, ExamDetailActivity.class);
                intent.putExtra("ma_de", maDe);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(examAdapter);
        loadExamData();
    }
    private void loadExamData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("de_thi");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Exam exam = ds.getValue(Exam.class);
                    if (exam != null) {
                        examList.add(exam);
                        countQuestionsForExam(exam.getMa_de()); // Đếm số câu hỏi cho mã đề
                    }
                }
                examAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ExamActivity.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void countQuestionsForExam(String maDe) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("cau hoi");
        ref.orderByChild("ma_de").equalTo(maDe)  // Lọc theo mã đề
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int questionCount = (int) snapshot.getChildrenCount();  // Đếm số câu hỏi trả về
                        Log.d("ExamActivity", "Mã đề: " + maDe + " - Tổng số câu hỏi: " + questionCount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ExamActivity.this, "Lỗi đếm câu hỏi", Toast.LENGTH_SHORT).show();
                    }
                });
    }




}
