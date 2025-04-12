package com.example.btl1;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Phương thức lấy danh sách câu hỏi theo mã nhóm câu hỏi
    public static void getQuestionsByTopic(String maNhomCauHoi, final DataCallback<List<Question>> callback) {
        // Truy vấn vào đúng node "cau_hoi"
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

    // Interface callback để nhận dữ liệu trả về
    public interface DataCallback<T> {
        void onDataLoaded(T data);
        void onDataError(Exception exception);
    }
}
