package com.example.btl1.Repostories;
import android.util.Log;

import com.example.btl1.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    // lấy danh sách câu hỏi theo mã nhóm câu hỏi
    public static void getQuestionsByTopic(String maNhomCauHoi, final DataCallback<List<Question>> callback) {
        // Truy vấn vào "cau_hoi"
        DatabaseReference reference = database.getReference("cau hoi");

        // Lọc các câu hỏi theo ma_nhom_cau_hoi
        reference.orderByChild("ma_nhom_cau_hoi").equalTo(maNhomCauHoi)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Question> questionList = new ArrayList<>();

                        // Log tất cả dữ liệu để kiểm tra
                        Log.d("Firebase", "Dữ liệu nhận được: " + dataSnapshot.toString());

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Question question = snapshot.getValue(Question.class);
                            if (question != null) {
                                questionList.add(question);
                            }
                        }

                        // Log số lượng câu hỏi lấy được
                        Log.d("Firebase", "Số câu hỏi tìm được: " + questionList.size());

                        // Nếu có câu hỏi, trả về dữ liệu
                        if (!questionList.isEmpty()) {
                            callback.onDataLoaded(questionList);
                        } else {
                            // Nếu không có câu hỏi, thông báo
                            callback.onDataLoaded(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Lỗi khi truy vấn Firebase
                        callback.onDataError(databaseError.toException());
                    }
                });
    }

    // Phương thức lấy tất cả câu hỏi
    public static void getAllQuestions(final DataCallback<List<Question>> callback) {
        DatabaseReference reference = database.getReference("cau hoi");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Question> questionList = new ArrayList<>();

                Log.d("Firebase", "Tất cả dữ liệu câu hỏi: " + dataSnapshot.toString());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    if (question != null) {
                        questionList.add(question);
                    }
                }

                Log.d("Firebase", "Tổng số câu hỏi: " + questionList.size());

                callback.onDataLoaded(questionList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataError(databaseError.toException());
            }
        });
    }
    // Phương thức lấy câu hỏi điểm liệt
    public static void getDiemLietQuestions(final DataCallback<List<Question>> callback) {
        DatabaseReference reference = database.getReference("cau hoi");

        reference.orderByChild("is_cau_diem_liet").equalTo("1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Question> questionList = new ArrayList<>();

                        Log.d("Firebase", "Dữ liệu điểm liệt nhận được: " + dataSnapshot.toString());

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Question question = snapshot.getValue(Question.class);
                            if (question != null) {
                                questionList.add(question);
                            }
                        }

                        Log.d("Firebase", "Số câu điểm liệt tìm được: " + questionList.size());

                        callback.onDataLoaded(questionList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onDataError(databaseError.toException());
                    }
                });
    }

    // Interface callback để nhận dữ liệu trả về
    public interface DataCallback<T> {
        void onDataLoaded(T data);
        void onDataError(Exception exception);
    }
}
