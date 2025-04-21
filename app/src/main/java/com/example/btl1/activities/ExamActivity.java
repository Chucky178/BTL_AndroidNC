package com.example.btl1.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.adapters.ExamAdapter;
import com.example.btl1.adapters.QuestionAdapter;
import com.example.btl1.R;
import com.example.btl1.models.Exam;
import com.example.btl1.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuestionAdapter adapter;
    private List<Question> questionList;
    private Button btnSubmit;
    private int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        RecyclerView recyclerView = findViewById(R.id.rvDeThi);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        List<Exam> examList = new ArrayList<>();
        ExamAdapter adapter = new ExamAdapter(this, examList);
        recyclerView.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("de_thi");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Exam exam = ds.getValue(Exam.class);
                    examList.add(exam);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ExamActivity.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadExamQuestions() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Questions").limit(25).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    questionList.add(document.toObject(Question.class));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void checkAnswers() {
//        correctAnswers = 0;
//        for (int i = 0; i < questionList.size(); i++) {
//            Question question = questionList.get(i);
//            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
//            if (viewHolder instanceof QuestionAdapter.ViewHolder) {
//                QuestionAdapter.ViewHolder qHolder = (QuestionAdapter.ViewHolder) viewHolder;
//                int selectedAnswer = qHolder.rgOptions.getCheckedRadioButtonId();
//                if (selectedAnswer == question.getCorrectAnswer()) {
//                    correctAnswers++;
//                }
//            }
//        }
        Toast.makeText(this, "Bạn trả lời đúng " + correctAnswers + "/" + questionList.size(), Toast.LENGTH_LONG).show();
    }
}
